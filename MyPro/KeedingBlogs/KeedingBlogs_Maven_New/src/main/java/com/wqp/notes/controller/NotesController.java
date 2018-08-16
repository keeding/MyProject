package com.wqp.notes.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wqp.notes.po.Notes;
import com.wqp.notes.service.NotesService;

@Controller
@RequestMapping("/notes")
public class NotesController {
	@Resource
	private NotesService ns;
	
	@RequestMapping("toNotes")
	public String toNotes(){
		System.out.println("Controller");
		return "blogIndex";
	}
	
	@RequestMapping("details")
	public String details(){
		return "details";
	}
	
	@RequestMapping("getNotes")
	@ResponseBody
	public Notes getNotes(){
		Notes n = ns.getNotes();
		return n;
	}
	
	@RequestMapping("getNotesList")
	@ResponseBody
	public List<Notes> getNotesList(){
		List<Notes> l = ns.getNotesList();
		return l;
	}
}
