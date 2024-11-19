INSERT INTO user
VALUES ('admin', 'admin@test.com');

INSERT INTO memo (writer, reader , img_num, text)
VALUES ('admin', 'admin' , 1, '더미 데이터입니다.');

SELECT * from memo;
SELECT * from user;
SELECT * from refresh_token;
