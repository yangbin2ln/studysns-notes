package com.insproject.provider.module.queue;

public interface QueueEventHandler<E> {

	void dequeueEventHandler(E peek) throws Exception;
}
