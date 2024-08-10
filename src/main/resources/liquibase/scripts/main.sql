-- liquibase formatted sql

-- changeset pavel-fomchenkov:1
CREATE TABLE IF NOT EXISTS public.users
(
     id bigint PRIMARY KEY,
     username character varying(255) NOT NULL,
     password character varying(255) NOT NULL,
     email character varying(255) NOT NULL,
     role character varying(32) NOT NULL
);
CREATE SEQUENCE user_id_seq
    AS BIGINT
    START WITH 1;

-- changeset pavel-fomchenkov:2
CREATE TABLE IF NOT EXISTS public.tasks
(
     id bigint PRIMARY KEY,
     description character varying(255) NOT NULL,
     start_date timestamp(6) NOT NULL,
     finish_date timestamp(6),
     status character varying(16) NOT NULL,
     priority character varying(16) NOT NULL
);
CREATE SEQUENCE task_id_seq
    AS BIGINT
    START WITH 1;

CREATE TABLE IF NOT EXISTS public.comments
(
     id bigint PRIMARY KEY,
     text character varying(255) NOT NULL
);
CREATE SEQUENCE comment_id_seq
    AS BIGINT
    START WITH 1;

-- changeset pavel-fomchenkov:3
ALTER TABLE public.tasks
    ADD COLUMN author_id bigint NOT NULL;

ALTER TABLE public.tasks
    ADD CONSTRAINT fk_author_id
    FOREIGN KEY (author_id)
    REFERENCES public.users(id);

-- changeset pavel-fomchenkov:4
ALTER TABLE public.comments
    ADD COLUMN author_id bigint NOT NULL;

ALTER TABLE public.comments
    ADD CONSTRAINT fk_author_id
    FOREIGN KEY (author_id)
    REFERENCES public.users(id);

ALTER TABLE public.comments
    ADD COLUMN task_id bigint NOT NULL;

ALTER TABLE public.comments
    ADD CONSTRAINT fk_task_id
    FOREIGN KEY (task_id)
    REFERENCES public.tasks(id);

-- changeset pavel-fomchenkov:5
ALTER TABLE public.comments
    ADD COLUMN creation_date timestamp(6) NOT NULL;

-- changeset pavel-fomchenkov:6
CREATE TABLE IF NOT EXISTS public.task_performers
(
    task_id bigint NOT NULL,
    user_id bigint NOT NULL
);

ALTER TABLE public.task_performers
    ADD CONSTRAINT fk_task_user
    FOREIGN KEY (task_id)
    REFERENCES public.tasks(id);

ALTER TABLE public.task_performers
    ADD CONSTRAINT fk_user_task
    FOREIGN KEY (user_id)
    REFERENCES public.users(id);