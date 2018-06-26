package com.insproject.provider.module.queue;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

public class QueueCache<E> {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	private QueueCache(){}
	
	private HashMap<String, InnerQueue<E>> queueMap = new HashMap<String, InnerQueue<E>>();
	
	public static class InnerQueue<E>{
		private LinkedBlockingQueue<E> linkedBlockQueue;
		private String keyName = "default";
		InnerQueue(int capacity){
			this(capacity, null);
		}

		InnerQueue(int capacity, String keyName){
			linkedBlockQueue = new LinkedBlockingQueue<E>(capacity);
			if(keyName != null){
				this.keyName = keyName;
			}
		}
		
		public E peek(){
			return linkedBlockQueue.peek();
		}
		
		public int size(){
			return linkedBlockQueue.size();
		}
		
		public boolean offset(E e){
			boolean flag = linkedBlockQueue.offer(e);
			synchronized (this) {
				this.notifyAll();
			}
			return flag;
		}

		public boolean offset(List<E> list){
			boolean flag = true;
			for(E e: list){
				flag = linkedBlockQueue.offer(e);
			}
			synchronized (this) {
				this.notifyAll();
			}
			return flag;
		}

		public E poll(){
			return linkedBlockQueue.poll();
		}
		
		@Override
		public String toString() {
			return this.keyName;
		}
	}
	
	private static QueueCache queueCache = null;
	public static<E> QueueCache<E> getInstance(){
		if(queueCache == null){
			synchronized (QueueCache.class) {
				if(queueCache == null){
					queueCache = new QueueCache<E>();
					
					
					
				}
			}
		}
		return queueCache;
	}
	
	public InnerQueue<E> register(String key, QueueEventHandler<E> queueEventHandler){
		if(!queueMap.containsKey(key)){
			InnerQueue<E> linkedBlockingQueue = new InnerQueue<E>(1024, key);
			InnerQueue<E> linkedBlockingQueueFaild = new InnerQueue<E>(1024, key + "failed");
			queueMap.put(key, linkedBlockingQueue);
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(true){
						if(linkedBlockingQueue.size() > 0){
							E peek = linkedBlockingQueue.peek();
							linkedBlockingQueue.poll();
							try {
								queueEventHandler.dequeueEventHandler(peek);
							} catch (Exception e) {
								linkedBlockingQueueFaild.offset(peek);
								logger.error("出队事件执行失败，数据已存入失败队列中。 " + linkedBlockingQueueFaild.toString());
								e.printStackTrace();
							}
						}else{
							synchronized (linkedBlockingQueue) {
								if(linkedBlockingQueue.size() == 0){
									try {
										linkedBlockingQueue.wait();
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}
						}
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
				}
			}).start();
		}
		return queueMap.get(key);
	}
	
	public static void main(String[] args) {
		
	}
	 
}
