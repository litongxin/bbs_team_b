package com.thoughtworks.bbs.mappers;

import com.thoughtworks.bbs.model.Post;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PostMapperTest extends MapperTestBase {
    PostMapper postMapper;
    Post post;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        postMapper = getSqlSession().getMapper(PostMapper.class);
        post = new Post().setAuthorName("juntao").setTitle("I am a post").setContent("content").setCreateTime(new Date())
                .setModifyTime(new Date()).setCreatorId(1L).setModifierId(1L).setParentId(0).setLikedTimes(0);
    }

    @Test
    public void shouldInsertANewPost() {
        int before = postMapper.getAll().size();

        postMapper.insert(post);

        assertThat(postMapper.getAll().size(), is(before + 1));
    }

    @Test
    public void shouldFindMainPostByAuthorName() {
        int before = postMapper.findMainPostByAuthorName("longkai").size();

        Post post1 = new Post().setAuthorName("longkai").setTitle("I am a post").setContent("content").setCreateTime(new Date())
                .setModifyTime(new Date()).setCreatorId(1L).setModifierId(1L).setParentId(11).setLikedTimes(0);
        Post post2 = new Post().setAuthorName("longkai").setTitle("I am a post").setContent("content").setCreateTime(new Date())
                .setModifyTime(new Date()).setCreatorId(1L).setModifierId(1L).setParentId(0).setLikedTimes(0);
        Post post3 = new Post().setAuthorName("juner").setTitle("I am a post").setContent("content").setCreateTime(new Date())
                .setModifyTime(new Date()).setCreatorId(1L).setModifierId(1L).setParentId(22).setLikedTimes(0);
        Post post4 = new Post().setAuthorName("longkai").setTitle("I am a post").setContent("content").setCreateTime(new Date())
                .setModifyTime(new Date()).setCreatorId(1L).setModifierId(1L).setParentId(0).setLikedTimes(0);
        postMapper.insert(post1);
        postMapper.insert(post2);
        postMapper.insert(post3);
        postMapper.insert(post4);

        List<Post> resultList = postMapper.findMainPostByAuthorName("longkai");

        assertThat(resultList.size(), is(before + 2));
    }

    @Test
    public void shouldFindAllPostsByAuthorName() throws Exception {
        List<Post> beforeList = postMapper.findAllPostsByAuthorName("yang");

        Post post1 = new Post().setAuthorName("yang").setTitle("I am a post").setContent("content").setCreateTime(new Date())
                .setModifyTime(new Date()).setCreatorId(1L).setModifierId(1L).setParentId(11L).setLikedTimes(0);
        Post post2 = new Post().setAuthorName("yang").setTitle("I am a post").setContent("content").setCreateTime(new Date())
                .setModifyTime(new Date()).setCreatorId(1L).setModifierId(1L).setParentId(0L).setLikedTimes(0);
        postMapper.insert(post1);
        postMapper.insert(post2);

        List<Post> afterList = postMapper.findAllPostsByAuthorName("yang");
        assertThat(afterList.size(), is(beforeList.size() + 2));
    }

    @Test
    public void shouldFindAllPost() {
        int before = postMapper.findAllPost().size();

        Post post1 = new Post().setAuthorName("longkai").setTitle("I am a post").setContent("content").setCreateTime(new Date())
                .setModifyTime(new Date()).setCreatorId(1L).setModifierId(1L).setParentId(11).setLikedTimes(0);
        Post post2 = new Post().setAuthorName("longkai").setTitle("I am a post").setContent("content").setCreateTime(new Date())
                .setModifyTime(new Date()).setCreatorId(1L).setModifierId(1L).setParentId(0).setLikedTimes(0);
        Post post3 = new Post().setAuthorName("juner").setTitle("I am a post").setContent("content").setCreateTime(new Date())
                .setModifyTime(new Date()).setCreatorId(1L).setModifierId(1L).setParentId(22).setLikedTimes(0);
        Post post4 = new Post().setAuthorName("longkai").setTitle("I am a post").setContent("content").setCreateTime(new Date())
                .setModifyTime(new Date()).setCreatorId(1L).setModifierId(1L).setParentId(0).setLikedTimes(0);
        postMapper.insert(post1);
        postMapper.insert(post2);
        postMapper.insert(post3);
        postMapper.insert(post4);

        List<Post> resultList = postMapper.findAllPost();

        assertThat(resultList.size(), is(before + 2));
    }


    @Test
    public void shouldFindAllPostByMainPost() {
        int before = postMapper.findAllPostByMainPost(3L).size();

        Post post1 = new Post().setAuthorName("longkai").setTitle("I am a post").setContent("content").setCreateTime(new Date())
                .setModifyTime(new Date()).setCreatorId(1L).setModifierId(1L).setParentId(3L).setLikedTimes(0);
        Post post2 = new Post().setAuthorName("longkai").setTitle("I am a post").setContent("content").setCreateTime(new Date())
                .setModifyTime(new Date()).setCreatorId(1L).setModifierId(1L).setParentId(2L).setLikedTimes(0);
        Post post3 = new Post().setAuthorName("juner").setTitle("I am a post").setContent("content").setCreateTime(new Date())
                .setModifyTime(new Date()).setCreatorId(1L).setModifierId(1L).setParentId(3L).setLikedTimes(0);
        Post post4 = new Post().setAuthorName("longkai").setTitle("I am a post").setContent("content").setCreateTime(new Date())
                .setModifyTime(new Date()).setCreatorId(1L).setModifierId(1L).setParentId(3L).setLikedTimes(0);
        postMapper.insert(post1);
        postMapper.insert(post2);
        postMapper.insert(post3);
        postMapper.insert(post4);

        List<Post> resultList = postMapper.findAllPostByMainPost(3L);

        assertThat(resultList.size(), is(before + 3));
    }

    @Test
    public void should_add_liked_times() {
        postMapper.insert(post);  //  fake one for empty
        List<Post> pl=postMapper.findAllPost();

        Long id=pl.get(0).getPostId();
        Post p = postMapper.get(id);
        Long before = p.getLikedTimes();
        for (int i = 0; i < 5; i++) {
            postMapper.add1LikedTime(id);
        }
        assertThat(postMapper.get(id).getLikedTimes(),is(before+5));
    }
}
