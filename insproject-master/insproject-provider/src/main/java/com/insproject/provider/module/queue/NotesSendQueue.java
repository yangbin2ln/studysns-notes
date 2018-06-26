package com.insproject.provider.module.queue;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.insproject.provider.module.notesdetails.entity.NotesDetails;
import com.insproject.provider.module.queue.QueueCache.InnerQueue;
import com.insproject.provider.module.ws.NotesSend;

/**
 * 笔记推送队列
 * @author yangbin
 *
 */
@Component
public class NotesSendQueue {

	@Autowired
	private NotesSend notesSend;
	
	Logger logger = Logger.getLogger(NotesSendQueue.class);

	public void add(NotesDetails e) {
		QueueCache<NotesDetails> instance = QueueCache.getInstance();
		InnerQueue<NotesDetails> register = instance.register("notesSend", new QueueEventHandler<NotesDetails>() {
			@Override
			public void dequeueEventHandler(NotesDetails peek) throws Exception {
				//推送
				notesSend.sendMessage(JSON.toJSONString(e));
			}
		});

		boolean success = register.offset(e);
		if (!success) {
			logger.error(register.toString() + "队列容量已满：" + register.size());
		}
	}

}
