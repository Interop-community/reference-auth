--
-- Turn off autocommit and start a transaction so that we can use the temp tables
--

SET AUTOCOMMIT = 0;

START TRANSACTION;

--
-- Insert client information into the temporary tables. To add clients to the HSQL database, edit things here.
--

INSERT INTO client_details (client_id, client_secret, client_name, dynamically_registered, refresh_token_validity_seconds, access_token_validity_seconds, id_token_validity_seconds, allow_introspection) VALUES
	('client', 'secret', 'Test Client', false, null, 3600, 600, true);

INSERT INTO client_scope (owner_id, scope) VALUES
	((SELECT id from client_details where client_id = 'client'), 'openid'),
	((SELECT id from client_details where client_id = 'client'), 'profile'),
	((SELECT id from client_details where client_id = 'client'), 'email'),
	((SELECT id from client_details where client_id = 'client'), 'address'),
	((SELECT id from client_details where client_id = 'client'), 'phone'),
	((SELECT id from client_details where client_id = 'client'), 'offline_access');

INSERT INTO client_redirect_uri (owner_id, redirect_uri) VALUES
	((SELECT id from client_details where client_id = 'client'), 'http://localhost/'),
	((SELECT id from client_details where client_id = 'client'), 'http://localhost:8080/');

INSERT INTO client_grant_type (owner_id, grant_type) VALUES
	((SELECT id from client_details where client_id = 'client'), 'authorization_code'),
	((SELECT id from client_details where client_id = 'client'), 'urn:ietf:params:oauth:grant_type:redelegate'),
	((SELECT id from client_details where client_id = 'client'), 'implicit'),
	((SELECT id from client_details where client_id = 'client'), 'refresh_token');

--
-- Close the transaction and turn autocommit back on
--

COMMIT;

SET AUTOCOMMIT = 1;

