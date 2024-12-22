INSERT INTO users(id, display_name, password, username)
VALUES (gen_random_uuid(), 'Test', '$2a$10$Av8tjzCsxXlrU71FStzCbucKQLnZtAQbj59zInPgqVX3E/687QbQm',
        'test_account') ON CONFLICT DO NOTHING;