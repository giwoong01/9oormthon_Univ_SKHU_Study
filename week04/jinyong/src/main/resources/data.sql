-- Dummy data for Member
INSERT INTO member (user_name, user_password, user_nickname, authority, social_type, email, year, university_name, name) VALUES
                                                                                                                             ('user1', 'password1', 'nickname1', 'ROLE_USER', 'SELF', 'user1@example.com', 23, 'University A', 'User One'),
                                                                                                                             ('user2', 'password2', 'nickname2', 'ROLE_USER', 'SELF', 'user2@example.com', 25, 'University B', 'User Two');

-- Dummy data for Post
INSERT INTO post (title, content, votes, comments, anonym, board_id, user_id) VALUES
                                                                                         ('First Post', 'This is the content of the first post', 10, 2, false, 1, 1),
                                                                                         ('Second Post', 'This is the content of the second post', 5, 1, true, 1, 2);
-- Dummy data for Comment
INSERT INTO comment (comment_content, user_id, post_id) VALUES
                                                     ('First comment on first post', 1, 1),
                                                     ('Second comment on first post', 2, 1),
                                                     ('First comment on second post', 1, 2);


