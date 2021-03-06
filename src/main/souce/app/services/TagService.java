package app.services;

import app.entities.entityActivity.Tag;

import java.util.List;

/**
 * Created by click on 5/19/2016.
 */
public interface TagService {
    public List<Tag> getAllTags();
    public Tag getTagById(int id);
    public Tag getTagByName(String name);
    public void incPostQuantity(int id);
    public void decPostQuantity(int id);
}
