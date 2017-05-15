package com.Forum.Dao;

import com.Forum.Entity.PubAnswer;
import com.Forum.Entity.PubMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created by Tomal on 2017-05-14.
 */
@Repository
public class PubMsgDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Collection<PubMsg> showPosts(String sort_by, String category) {

        String sql;
        List<PubMsg> users;

        if(category.equals("wszystkie")) {
            sql = String.format("SELECT * FROM public_message ORDER BY %s DESC", sort_by);
            users = jdbcTemplate.query(sql, new BeanPropertyRowMapper(PubMsg.class));
        }else{
            sql = String.format("SELECT * FROM public_message WHERE category=? ORDER BY %s DESC", sort_by);
            users = jdbcTemplate.query(sql, new BeanPropertyRowMapper(PubMsg.class),category);

        }


        return users;
    }


    public Collection<PubAnswer> showAnswers(int id) {
        String sql = "SELECT * FROM public_answer WHERE public_message_id=? ORDER BY date ASC";

        List<PubAnswer> answers = jdbcTemplate.query(sql,
                new BeanPropertyRowMapper(PubMsg.class), id);

        return answers;
    }

    public Collection<PubMsg> showSinglePost(int id) {
        String sql = "SELECT * FROM public_message WHERE public_message_id=?";

        List<PubMsg> singlePost = jdbcTemplate.query(sql,
                new BeanPropertyRowMapper(PubMsg.class), id);

        return singlePost;
    }

    public void addAnswer(int id, String user_name, String pub_answer) {
        final String sql = "INSERT INTO public_answer (user_name,public_message_id,content) VALUES (?,?,?)";



        jdbcTemplate.update(sql, new Object[]{user_name, id, pub_answer});
    }

    public void addPubMsg(String category, String user_name, String content, String topic) {
        final String sql = "INSERT INTO public_message (user_name, topic, content, category) VALUES (?,?,?,?)";

        jdbcTemplate.update(sql, new Object[]{user_name, topic, content, category});


    }

    public int checkLikes(int public_message_id, String user_name) {
        int total = 0;
        try {
            final String sql = "SELECT like_id FROM likes WHERE public_message_id = ? AND user_name = ?";
            total = jdbcTemplate.queryForObject(sql, int.class, public_message_id, user_name);


            return 1;
        } catch (EmptyResultDataAccessException e) {
            return 0;

        }
    }

    public void addLike(int public_message_id, String user_name) {
        int total = this.checkLikes(public_message_id, user_name);


        final String sql1 = "UPDATE public_message SET ilosc_plusow = ilosc_plusow + 1 WHERE public_message_id = ?";

        if (total == 0) jdbcTemplate.update(sql1, new Object[]{public_message_id});

        final String sql2 = "INSERT INTO likes (public_message_id, user_name) VALUES (?,?)";

        if (total == 0) jdbcTemplate.update(sql2, new Object[]{public_message_id, user_name});

    }
}
