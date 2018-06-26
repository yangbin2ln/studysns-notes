package com.insproject.provider.module.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.insproject.provider.module.notesdetails.entity.NotesDetails;
import com.insproject.provider.module.queue.NotesSendQueue;

@Component("pullNotesJob")
public class PullNotesJob {
	
	@Autowired
	private NotesSendQueue notesSendQueue;
	
	public void run() {
		NotesDetails notesDetails = new NotesDetails();
		notesDetails.setTitle("啦啦啦啊");
		notesSendQueue.add(notesDetails);
	}
}
