--
-- PostgreSQL database dump
--

-- Dumped from database version 15.3
-- Dumped by pg_dump version 15.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Data for Name: genre; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.genre VALUES ('GR01', 'homme');
INSERT INTO public.genre VALUES ('GR02', 'femme');


--
-- Data for Name: utilisateur; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.utilisateur VALUES ('USR000000001', 'leadupuis@gmail.com', '$2y$10$tm/Fk7GMBvJBagWQ8R2sne7m/WhNA0RH5hNMqmElPofAp4HEn9bZm', 'DUPUIS', 'L├®a', '1992-03-14', 'GR02', 'n97If9WeX&y,;QcD');
INSERT INTO public.utilisateur VALUES ('USR000000002', 'lucmartin@hotmail.com', '$2y$10$7cJb9yJWognbYbJdM1/g1OvCo5lB36Nurraznh48QhkTQebi74d4W', 'MARTIN', 'Luc', '1988-07-01', 'GR01', '/i*=rlQdmQ87t5y#');
INSERT INTO public.utilisateur VALUES ('USR000000003', 'sophiebernard@gmail.com', '$2y$10$B92j2YXJSeG5UR06UYelXuhuoOXKXQMubCTvgy1TR/dyij6yrRWie', 'BERNARD', 'Sophie', '1995-11-28', 'GR02', '.?7.9}zHA#47!-SP');
INSERT INTO public.utilisateur VALUES ('USR000000004', 'marcrobert@gmail.com', '$2y$10$5tmjaJS2fZENyy.oUlsxS.z5mWZKBMZxJ0VLGathoHpLSqa6Du6U2', 'ROBERT', 'Marc', '1990-06-17', 'GR01', 'yi%3,gn_]hw)BIIb');
INSERT INTO public.utilisateur VALUES ('USR000000005', 'emiliedurand@ymail.com', '$2y$10$0atHc19WzXZ5tico91S0PuS8XSHLnjF268zjYtyOirRjvRLkuxpTq', 'DURAND', '├ëmilie', '1987-09-04', 'GR02', 'm_lYvlF^$*-FFR!k');
INSERT INTO public.utilisateur VALUES ('USR000000006', 'pierreleclerc@ymail.com', '$2y$10$0B5OIUZ/xlt5GqrSyeFHJOEO1n43QSacNO25bpTEx3.pjOJLUs2UO', 'LECLERC', 'Pierre', '1993-04-21', 'GR01', '|m)^2+/}b:F|Ra-q');
INSERT INTO public.utilisateur VALUES ('USR000000007', 'claireleroy@ymail.com', '$2y$10$B/D/pi/1WR1eLcziNwcdKeTkrAaFdDqoLpY1Auakp8OGMM/YMKpvK', 'LEROY', 'Claire', '1989-12-09', 'GR02', '}[[**C1]y@46-||&');
INSERT INTO public.utilisateur VALUES ('USR000000008', 'nicolasandre@gmail.com', '$2y$10$YbFQo2kqrJr6lqgzWML0ducugxFMTCADMPYte/AjtqEjSCQaZgsHe', 'ANDR├ë', 'Nicolas', '1991-02-07', 'GR01', 'KuB|se2D=lfioF:M');
INSERT INTO public.utilisateur VALUES ('USR000000009', 'alicemoreau@hotmail.com', '$2y$10$zcN6p9bQXhI.z.oDghNBNusjolLWbEsqkanxF2oTCXxa0pkxg6/.u', 'MOREAU', 'Alice', '1994-08-24', 'GR02', '|aRwX<fD75htX@Xr');
INSERT INTO public.utilisateur VALUES ('USR000000010', 'antoinesimon@ymail.com', '$2y$10$huSWsyEQ0Pp2bxryFeY1DOvv01GfVoXsFghh8lcbtLxzkE8iXkVjK', 'SIMON', 'Antoine', '1986-01-13', 'GR01', '<it1<X13@o+Z<+KZ');


--
-- Data for Name: compte; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.compte VALUES ('CMT000000001', 0, NULL, NULL, NULL, 'USR000000001');
INSERT INTO public.compte VALUES ('CMT000000002', 0, NULL, NULL, NULL, 'USR000000002');
INSERT INTO public.compte VALUES ('CMT000000003', 0, NULL, NULL, NULL, 'USR000000003');
INSERT INTO public.compte VALUES ('CMT000000004', 0, NULL, NULL, NULL, 'USR000000004');
INSERT INTO public.compte VALUES ('CMT000000005', 0, NULL, NULL, NULL, 'USR000000005');
INSERT INTO public.compte VALUES ('CMT000000006', 0, NULL, NULL, NULL, 'USR000000006');
INSERT INTO public.compte VALUES ('CMT000000007', 0, NULL, NULL, NULL, 'USR000000007');
INSERT INTO public.compte VALUES ('CMT000000008', 0, NULL, NULL, NULL, 'USR000000008');
INSERT INTO public.compte VALUES ('CMT000000009', 0, NULL, NULL, NULL, 'USR000000009');
INSERT INTO public.compte VALUES ('CMT000000010', 0, NULL, NULL, NULL, 'USR000000010');


--
-- Data for Name: pin; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: tentative; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: token_compte; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Name: s_compte; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.s_compte', 10, true);


--
-- Name: s_genre; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.s_genre', 2, true);


--
-- Name: s_pin; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.s_pin', 1, false);


--
-- Name: s_tentative; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.s_tentative', 1, false);


--
-- Name: s_token; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.s_token', 1, false);


--
-- Name: s_utilisateur; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.s_utilisateur', 10, true);


--
-- PostgreSQL database dump complete
--

