package cn.zjut.wangjie.pushpaper.service;

import cn.zjut.wangjie.pushpaper.pojo.Note;

import java.util.List;

public interface NoteService {

    List<Note> listNoteByPaperIdAndUserId(Integer paperId , Integer userId);
    int addNote(Note note);
    Note getNoteById(Integer noteId);

    int updateNote(Note note);

    int deleteNote(Integer noteId);

    List<Note> listNoteByUserId(Integer userId);
}
