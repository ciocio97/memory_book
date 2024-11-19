CREATE TABLE IF NOT EXISTS memo(
memo_id INT PRIMARY KEY AUTO_INCREMENT,
writer VARCHAR(20) NOT NULL,
reader VARCHAR(20), 
img_num INT NOT NULL,
text VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS user(
	user_id VARCHAR(20) PRIMARY KEY,
    user_email VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS refresh_token(
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- 토큰 ID
    user_id BIGINT NOT NULL,             -- 사용자 ID (Foreign Key)
    token VARCHAR(512) NOT NULL,         -- 리프레시 토큰
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 생성 시각
    expires_at TIMESTAMP NOT NULL        -- 만료 시각
);