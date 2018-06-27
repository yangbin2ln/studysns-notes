package com.insproject.provider.module.job;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.insproject.provider.module.notesdetails.service.NotesDetailsService;
import com.insproject.provider.module.queue.NotesSendQueue;

@Component("pullNotesJob")
public class PullNotesJob {
	
	@Autowired
	private NotesSendQueue notesSendQueue;
	
	@Autowired
	private NotesDetailsService notesDetailsService;
	
	public void run() {	
//		List<Map<String, Object>> loadSummaryList = notesDetailsService.loadSummaryList();
//		notesSendQueue.add(loadSummaryList);
	}
}
