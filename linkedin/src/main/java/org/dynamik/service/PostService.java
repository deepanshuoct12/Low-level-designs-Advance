package org.dynamik.service;

import org.dynamik.dao.PostDao;
import org.dynamik.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PostService {
    private final PostDao postDao;

    public PostService() {
        this.postDao = new PostDao();
    }

    public Post createPost(Post post) {
        if (post.getId() == null || post.getId().trim().isEmpty()) {
            post.setId(UUID.randomUUID().toString());
        }

        return postDao.save(post);
    }

    public Post getPostById(String postId) {
        return postDao.findById(postId);
    }

    public List<Post> getAllPosts() {
        return postDao.findAll();
    }

    public List<Post> getPostsByUser(String userId) {
        return postDao.findByUserId(userId);
    }

    public List<Post> getPostsByUser(List<String> userIds) {
        List<Post> posts = new ArrayList<>();
        for (String userId : userIds) {
            posts.addAll(postDao.findByUserId(userId));
        }

        return posts;
    }

    public void deletePost(String postId) {
        postDao.delete(postId);
    }

    public List<Post> searchPosts(String searchText) {
        return postDao.searchInContent(searchText);
    }

    public List<Post> getRecentPosts(int page, int size) {
        return postDao.findRecentPosts(page, size);
    }

    public Post createPost(String userId, String postId) {
        Post post = new Post();
        post.setId(postId);
        post.setUserId(userId);
        return postDao.save(post);
    }
}
