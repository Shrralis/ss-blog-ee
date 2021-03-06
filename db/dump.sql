--
-- PostgreSQL database dump
--

-- Dumped from database version 10.1
-- Dumped by pg_dump version 10.1

-- Started on 2017-12-05 19:16:48 EET

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = ON;
SET check_function_bodies = FALSE;
SET client_min_messages = WARNING;
SET row_security = OFF;

--
-- TOC entry 1 (class 3079 OID 12290)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;

--
-- TOC entry 2214 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = FALSE;

--
-- TOC entry 202 (class 1259 OID 16438)
-- Name: images; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE images (
    id integer NOT NULL,
    src character varying(512) NOT NULL
);

--
-- TOC entry 201 (class 1259 OID 16436)
-- Name: images_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE images_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

--
-- TOC entry 2215 (class 0 OID 0)
-- Dependencies: 201
-- Name: images_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE images_id_seq OWNED BY images.id;

--
-- TOC entry 199 (class 1259 OID 16396)
-- Name: posts; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE posts (
    id integer NOT NULL,
    title character varying(64) NOT NULL,
    description character varying(128) NOT NULL,
    text character varying(2048) NOT NULL,
    is_posted boolean NOT NULL,
    creator_id integer NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone,
    image_id integer
);

--
-- TOC entry 198 (class 1259 OID 16394)
-- Name: posts_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE posts_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

--
-- TOC entry 2216 (class 0 OID 0)
-- Dependencies: 198
-- Name: posts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE posts_id_seq OWNED BY posts.id;

--
-- TOC entry 200 (class 1259 OID 16405)
-- Name: posts_updaters; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE posts_updaters (
    post_id integer NOT NULL,
    user_id integer NOT NULL
);

--
-- TOC entry 197 (class 1259 OID 16388)
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE users (
    id integer NOT NULL,
    login character varying(16) NOT NULL,
    password character varying(256) NOT NULL,
    scope character(8) NOT NULL
);

--
-- TOC entry 196 (class 1259 OID 16386)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

--
-- TOC entry 2217 (class 0 OID 0)
-- Dependencies: 196
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;

--
-- TOC entry 2055 (class 2604 OID 16441)
-- Name: images id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY images
  ALTER COLUMN id SET DEFAULT nextval('images_id_seq' :: REGCLASS);

--
-- TOC entry 2054 (class 2604 OID 16399)
-- Name: posts id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY posts
  ALTER COLUMN id SET DEFAULT nextval('posts_id_seq' :: REGCLASS);

--
-- TOC entry 2053 (class 2604 OID 16391)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
  ALTER COLUMN id SET DEFAULT nextval('users_id_seq' :: REGCLASS);

--
-- TOC entry 2207 (class 0 OID 16438)
-- Dependencies: 202
-- Data for Name: images; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO images VALUES (1, 'ladder_purple_light_118353_1920x1080.jpg');
INSERT INTO images VALUES (2, 'ladder_purple_light_118353_1920x1080.jpg');
INSERT INTO images VALUES (3, 'photo_2017-10-21_00-46-45.jpg');
INSERT INTO images VALUES (5, 'cute-dog-shiba-inu-ryuji-japan-57.jpg');
INSERT INTO images VALUES (9, 'cute-dog-shiba-inu-ryuji-japan-57.jpg');
INSERT INTO images VALUES (10, 'photo_2017-10-21_00-46-45.jpg');
INSERT INTO images VALUES (11, 'ladder_purple_light_118353_1920x1080.jpg');
INSERT INTO images VALUES (12, 'tmm.gif');

--
-- TOC entry 2204 (class 0 OID 16396)
-- Dependencies: 199
-- Data for Name: posts; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO posts
VALUES (15, 'Test post without image', 'test', 'tost', FALSE, 2, '2017-12-04 01:38:25.551931', NULL, NULL);
INSERT INTO posts VALUES (1, 'Lolkaaa', 'Lolkaaa', 'Azaza AMA CODILO!134455', TRUE, 3, '2017-11-27 17:55:15.729276',
                          '2017-11-28 18:18:16.45661', NULL);
INSERT INTO posts VALUES (6, 'Test post', 'This post is an example', 'Hey, what''s up? Shrralis here.

Now I will show you how the post will be displayed on the website.

Next is some big text.

Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab adipisci animi architecto corporis cum, distinctio dolores eos, facere in ipsa laborum minima obcaecati optio quae quo recusandae ullam unde vero.',
                          FALSE, 2, '2017-11-29 13:02:34.613294', NULL, NULL);
INSERT INTO posts VALUES (9, 'Тест', 'Тест кирилиці й одночасно довгого посту',
                          'Hey, what''s up? Shrralis here. Now I will show you how the post will be displayed on the website. Next is some big text. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab adipisci animi architecto corporis cum, distinctio dolores eos, facere in ipsa laborum minima obcaecati optio quae quo recusandae ullam unde vero. Hey, what''s up? Shrralis here. Now I will show you how the post will be displayed on the website. Next is some big text. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab adipisci animi architecto corporis cum, distinctio dolores eos, facere in ipsa laborum minima obcaecati optio quae quo recusandae ullam unde vero. Hey, what''s up? Shrralis here. Now I will show you how the post will be displayed on the website. Next is some big text. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab adipisci animi architecto corporis cum, distinctio dolores eos, facere in ipsa laborum minima obcaecati optio quae quo recusandae ullam unde vero.',
                          FALSE, 2, '2017-11-29 13:24:58.443933', NULL, NULL);
INSERT INTO posts VALUES (10, 'About Shrralis', 'Something',
                          'Ololo and tralala AMA CODILO AND MY NAME IS SHRRALIS. ACTUALLY MY NAME IS YAROSLAV BUT I LIKE SHRRALIS MORE THAN MY ORIGINAL NAME SO... I DON''T CARE.',
                          FALSE, 2, '2017-11-29 13:52:44.794752', NULL, NULL);
INSERT INTO posts VALUES (18, '9', '9', '9', FALSE, 2, '2017-12-04 17:16:18.104471', NULL, NULL);
INSERT INTO posts VALUES (19, '10', '10', '10', FALSE, 2, '2017-12-04 17:16:46.009823', NULL, NULL);
INSERT INTO posts VALUES (20, '11', '11', '11', FALSE, 2, '2017-12-04 17:16:52.241394', NULL, NULL);
INSERT INTO posts VALUES (21, '12', '12', '12', FALSE, 2, '2017-12-04 17:50:26.399962', NULL, NULL);
INSERT INTO posts VALUES
  (22, 'Test', 'Test post for searching', 'You know what to do', FALSE, 2, '2017-12-04 18:31:35.900814', NULL, NULL);
INSERT INTO posts
VALUES (23, 'Another test', 'Lol', 'I love that sh*t', FALSE, 2, '2017-12-04 18:31:50.276215', NULL, NULL);
INSERT INTO posts
VALUES (24, 'Test with letter ''e''', 'Azaza', 'Kekboxing', FALSE, 2, '2017-12-04 18:32:06.396794', NULL, NULL);
INSERT INTO posts VALUES
  (25, 'I am a kekboxer', 'Kekboxing is cool1', 'HELLO WORD! IT IS EMPTY FILE', FALSE, 2, '2017-12-04 18:32:31.175482',
   '2017-12-04 19:20:16.907752', NULL);
INSERT INTO posts VALUES
  (17, 'Shiba', 'I just LOVE Shibas', 'They are the most beautiful dogs.', TRUE, 2, '2017-12-04 01:40:14.987409',
   '2017-12-05 03:00:15.604629', 9);
INSERT INTO posts VALUES
  (13, 'Test post with a picture', 'Lol', 'You know what to do', FALSE, 2, '2017-11-30 07:31:28.314345',
   '2017-12-05 03:00:46.846473', 11);
INSERT INTO posts VALUES (14, 'Shrralis welcomes you', 'HELLO MY FRIEND!',
                          'Я щиро вдячний Вам за відвідання цієї прекраснї сторінки мого особистого закритого блогу, якщо Ви це бачите, Ви - хакер, або - ЕЛІТА!',
                          TRUE, 2, '2017-11-30 16:55:23.155557', '2017-12-05 03:00:37.441287', 10);
INSERT INTO posts VALUES (26, 'T', 'D', 't', FALSE, 2, '2017-12-05 18:59:41.637656', NULL, 12);

--
-- TOC entry 2205 (class 0 OID 16405)
-- Dependencies: 200
-- Data for Name: posts_updaters; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO posts_updaters VALUES (1, 2);
INSERT INTO posts_updaters VALUES (25, 1);
INSERT INTO posts_updaters VALUES (25, 3);

--
-- TOC entry 2202 (class 0 OID 16388)
-- Dependencies: 197
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO users VALUES (1, 'test', '25ab3b38f7afc116f18fa9821e44d561', 'READER  ');
INSERT INTO users VALUES (4, 'banned', '279c235dfa4dc62bed56ab8851f6378d', 'BANNED  ');
INSERT INTO users VALUES (2, 'admin', '77e2edcc9b40441200e31dc57dbb8829', 'ADMIN   ');
INSERT INTO users VALUES (3, 'writer', '65204f6a1244699add60f493c31a36fc', 'WRITER  ');
INSERT INTO users VALUES (5, 'reader', '81d20dcf320529a9d2d69f3ad43e3b84', 'READER  ');
INSERT INTO users VALUES (6, 'post', '1b961151a4d9563a31438aa0cc6b71ee', 'READER  ');
INSERT INTO users VALUES (7, 'tost', '1591b4611dc05b0fed1bf7c3c5365fbe', 'READER  ');

--
-- TOC entry 2218 (class 0 OID 0)
-- Dependencies: 201
-- Name: images_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('images_id_seq', 12, TRUE);

--
-- TOC entry 2219 (class 0 OID 0)
-- Dependencies: 198
-- Name: posts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('posts_id_seq', 26, TRUE);

--
-- TOC entry 2220 (class 0 OID 0)
-- Dependencies: 196
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('users_id_seq', 7, TRUE);

--
-- TOC entry 2075 (class 2606 OID 16446)
-- Name: images images_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY images
    ADD CONSTRAINT images_pkey PRIMARY KEY (id);

--
-- TOC entry 2066 (class 2606 OID 16404)
-- Name: posts posts_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY posts
    ADD CONSTRAINT posts_pkey PRIMARY KEY (id);

--
-- TOC entry 2072 (class 2606 OID 16409)
-- Name: posts_updaters posts_updaters_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY posts_updaters
    ADD CONSTRAINT posts_updaters_pkey PRIMARY KEY (post_id, user_id);

--
-- TOC entry 2059 (class 2606 OID 16393)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);

--
-- TOC entry 2061 (class 1259 OID 16418)
-- Name: fki_posts_creator_id; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX fki_posts_creator_id
  ON posts USING BTREE (creator_id);

--
-- TOC entry 2062 (class 1259 OID 16453)
-- Name: fki_posts_image_id; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX fki_posts_image_id
  ON posts USING BTREE (image_id);

--
-- TOC entry 2069 (class 1259 OID 16429)
-- Name: fki_posts_updaters_post_id; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX fki_posts_updaters_post_id
  ON posts_updaters USING BTREE (post_id);

--
-- TOC entry 2070 (class 1259 OID 16435)
-- Name: fki_posts_updaters_user_id; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX fki_posts_updaters_user_id
  ON posts_updaters USING BTREE (user_id);

--
-- TOC entry 2073 (class 1259 OID 16447)
-- Name: images_id_index; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX images_id_index
  ON images USING BTREE (id);

--
-- TOC entry 2063 (class 1259 OID 16422)
-- Name: posts_description_index; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX posts_description_index
  ON posts USING BTREE (description);

--
-- TOC entry 2064 (class 1259 OID 16420)
-- Name: posts_id_index; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX posts_id_index
  ON posts USING BTREE (id);

--
-- TOC entry 2067 (class 1259 OID 16423)
-- Name: posts_text_index; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX posts_text_index
  ON posts USING BTREE (text);

--
-- TOC entry 2068 (class 1259 OID 16421)
-- Name: posts_title_index; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX posts_title_index
  ON posts USING BTREE (title);

--
-- TOC entry 2056 (class 1259 OID 16410)
-- Name: users_id_index; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX users_id_index
  ON users USING BTREE (id);

--
-- TOC entry 2057 (class 1259 OID 16411)
-- Name: users_login_index; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX users_login_index
  ON users USING BTREE (login);

--
-- TOC entry 2060 (class 1259 OID 16412)
-- Name: users_scope_index; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX users_scope_index
  ON users USING BTREE (scope);

--
-- TOC entry 2076 (class 2606 OID 16413)
-- Name: posts posts_creator_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY posts
    ADD CONSTRAINT posts_creator_id FOREIGN KEY (creator_id) REFERENCES users(id);

--
-- TOC entry 2077 (class 2606 OID 16454)
-- Name: posts posts_image_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY posts
    ADD CONSTRAINT posts_image_id FOREIGN KEY (image_id) REFERENCES images(id) ON UPDATE CASCADE ON DELETE SET NULL;

--
-- TOC entry 2078 (class 2606 OID 16424)
-- Name: posts_updaters posts_updaters_post_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY posts_updaters
    ADD CONSTRAINT posts_updaters_post_id FOREIGN KEY (post_id) REFERENCES posts(id);

--
-- TOC entry 2079 (class 2606 OID 16430)
-- Name: posts_updaters posts_updaters_user_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY posts_updaters
    ADD CONSTRAINT posts_updaters_user_id FOREIGN KEY (user_id) REFERENCES users(id);

-- Completed on 2017-12-05 19:16:48 EET

--
-- PostgreSQL database dump complete
--

