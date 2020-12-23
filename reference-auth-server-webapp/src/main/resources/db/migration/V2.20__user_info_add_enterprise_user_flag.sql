ALTER TABLE user_info ADD enterprise_user bool default false;
UPDATE user_info SET enterprise_user = false;