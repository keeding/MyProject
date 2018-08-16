package com.wqp.notes.service;

import java.util.List;

import com.wqp.notes.po.Notes;

public interface NotesService {
	public Notes getNotes();

	public List<Notes> getNotesList();
}
