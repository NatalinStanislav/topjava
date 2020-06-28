DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, description, date_time, calories) VALUES
(100000, 'завтрак', '2020-06-27T10:00:00', 500),
(100000, 'обед', '2020-06-27T13:00:00', 1000),
(100000, 'ужин', '2020-06-27T20:00:00', 500),
(100000, 'граница', '2020-06-28T00:00:00', 100),
(100000, 'завтрак', '2020-06-28T10:00:00', 500),
(100000, 'обед', '2020-06-28T13:00:00', 1000),
(100000, 'ужин', '2020-06-28T20:00:00', 500),
(100001, 'завтрак админа ролтон с курицей', '2020-06-27T12:00:00', 1000),
(100001, 'ужин админа ролтон с говядиной', '2020-06-27T23:00:00', 1000);
