package com.wqp.notes.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wqp.notes.dao.NotesMapper;
import com.wqp.notes.po.Notes;
import com.wqp.notes.po.NotesExample;
import com.wqp.notes.service.NotesService;

@Service
public class NotesServiceImpl implements NotesService {
	@Resource
	private NotesMapper notesMapper;
	
	public Notes getNotes(){
		Notes n = notesMapper.selectByPrimaryKey(1);
		return n;
	}

	@Override
	public List<Notes> getNotesList() {
		List<Notes> selectByExample = notesMapper.selectByExample(new NotesExample());
		System.out.println(selectByExample);
		return selectByExample;
	}
}
