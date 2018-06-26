package com.insproject.provider.module.queue;

import org.apache.log4j.Logger;

import com.insproject.provider.module.queue.QueueCache.InnerQueue;
import com.insproject.provider.module.queue.util.MailEntity;
import com.insproject.provider.module.queue.util.MailSendUtil;

public class MailSendQueue {

	static Logger logger = Logger.getLogger(MailSendQueue.class);

	public static void add(MailEntity e) {
		QueueCache<MailEntity> instance = QueueCache.getInstance();
		InnerQueue<MailEntity> register = instance.register("mailSend", new QueueEventHandler<MailEntity>() {
			@Override
			public void dequeueEventHandler(MailEntity peek) throws Exception {
				MailSendUtil.sendMail(peek);
			}
		});

		boolean success = register.offset(e);
		if (!success) {
			logger.error(register.toString() + "队列容量已满：" + register.size());
		}
	}

	public static void main(String[] args) {
		MailEntity e = new MailEntity();
		e.setReceiveMailAccounts(new String[] { "465471659@qq.com" });
		e.setTitle("test");
		MailSendQueue.add(e);
	}
}
