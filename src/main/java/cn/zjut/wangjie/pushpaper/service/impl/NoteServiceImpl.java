package cn.zjut.wangjie.pushpaper.service.impl;

import cn.zjut.wangjie.pushpaper.mapper.NoteDao;
import cn.zjut.wangjie.pushpaper.pojo.Note;
import cn.zjut.wangjie.pushpaper.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: pushpaper
 * @description:
 * @author: WangJie
 * @create: 2018-06-09 12:38
 **/
@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteDao noteDao;
    @Override
    public List<Note> listNoteByPaperIdAndUserId(Integer paperId, Integer userId) {
        Note note = new Note();
        note.setUserId(userId);
        note.setPaperId(paperId);
        List<Note> noteList = noteDao.listNoteByPaperIdAndUserId(note);
        return noteList;
    }

    @Override
    public int addNote(Note note) {
        note.setAddAt(System.currentTimeMillis());
        return noteDao.addNote(note);
    }

    @Override
    public Note getNoteById(Integer noteId) {

        return noteDao.getNoteById(noteId);
    }

    @Override
    public int updateNote(Note note) {
        note.setUpdateAt(System.currentTimeMillis());
        return noteDao.updateNote(note);

    }

    @Override
    public int deleteNote(Integer noteId) {
        return noteDao.deleteNote(noteId);
    }

    @Override
    public List<Note> listNoteByUserId(Integer userId) {
        return noteDao.listNotesByUserId(userId);
    }
}
