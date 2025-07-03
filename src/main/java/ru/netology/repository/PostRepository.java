package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {
  private final Map<Long, Post> posts = new ConcurrentHashMap<>();
  private final AtomicLong counter = new AtomicLong();

  public List<Post> all() {
    return new ArrayList<>(posts.values());
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(posts.get(id));
  }

  public Post save(Post post) {
    if (post.getId() == 0) {
      long id = counter.incrementAndGet();
      post.setId(id);
      posts.put(id, post);
    } else if (posts.containsKey(post.getId())) {
      posts.put(post.getId(), post);
    } else {
      throw new NotFoundException("Post with id " + post.getId() + " not found");
    }
    return post;
  }

  public void removeById(long id) {
    if (!posts.containsKey(id)) {
      throw new NotFoundException("Post with id " + id + " not found");
    }
    posts.remove(id);
  }
}