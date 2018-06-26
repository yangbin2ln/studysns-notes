package com.insproject.provider.module.queue;

import java.util.List;
import java.util.Map;

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

	public void add(Map<String,Object> e) {
		QueueCache<Map<String,Object>> instance = QueueCache.getInstance();
		InnerQueue<Map<String,Object>> register = instance.register("notesSend", new QueueEventHandler<Map<String,Object>>() {
			@Override
			public void dequeueEventHandler(Map<String,Object> peek) throws Exception {
				//推送
				notesSend.sendMessage(JSON.toJSONString(e));
			}
		});

		boolean success = register.offset(e);
		if (!success) {
			logger.error(register.toString() + "队列容量已满：" + register.size());
		}
	}

	public void add(List<Map<String,Object>> e) {
		QueueCache<Map<String,Object>> instance = QueueCache.getInstance();
		InnerQueue<Map<String,Object>> register = instance.register("notesSend", new QueueEventHandler<Map<String,Object>>() {
			@Override
			public void dequeueEventHandler(Map<String,Object> peek) throws Exception {
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
