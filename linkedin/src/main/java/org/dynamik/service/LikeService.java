package org.dynamik.service;

import org.dynamik.dao.LikeDao;
import org.dynamik.model.Like;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class LikeService {
    private final LikeDao likeDao;

    public LikeService() {
        this.likeDao = new LikeDao();
    }

    public Like addLike(Like like) {
        if (likeDao.existsByUserAndPost(like.getUserId(), like.getPostId())) {
            throw new IllegalStateException("User has already liked this post");
        }


        if (Objects.isNull(like.getId())) {
            like.setId(UUID.randomUUID().toString());
        }

        return likeDao.save(like);
    }

    public void removeLike(String likeId) {
        likeDao.delete(likeId);
    }

    public void removeLikeByUserAndPost(String userId, String postId) {
        likeDao.removeByUserAndPost(userId, postId);
    }

    public List<Like> getLikesByPost(String postId) {
        return likeDao.findByPostId(postId);
    }

    public List<Like> getLikesByUser(String userId) {
        return likeDao.findByUserId(userId);
    }

    public boolean hasUserLikedPost(String userId, String postId) {
        return likeDao.existsByUserAndPost(userId, postId);
    }
}
