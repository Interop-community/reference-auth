INSERT INTO client_details (client_id, client_name, logo_uri, access_token_validity_seconds, token_endpoint_auth_method) VALUES
  ('patient_data_manager', 'Patient Data Manager App', 'https://content.hspconsortium.org/images/hspc-patient-data-manager/logo/pdm.png', 86400, 'NONE');

INSERT INTO client_scope (owner_id, scope) VALUES
  ((SELECT id from client_details where client_id = 'patient_data_manager'), 'launch'),
  ((SELECT id from client_details where client_id = 'patient_data_manager'), 'profile'),
  ((SELECT id from client_details where client_id = 'patient_data_manager'), 'offline_access'),
  ((SELECT id from client_details where client_id = 'patient_data_manager'), 'launch'),
  ((SELECT id from client_details where client_id = 'patient_data_manager'), 'patient/*.*'),
  ((SELECT id from client_details where client_id = 'patient_data_manager'), 'patient/*.read'),
  ((SELECT id from client_details where client_id = 'patient_data_manager'), 'patient/*.write');

INSERT INTO client_grant_type (owner_id, grant_type) VALUES
  ((SELECT id from client_details where client_id = 'patient_data_manager'), 'authorization_code');

INSERT INTO client_redirect_uri (owner_id, redirect_uri) VALUES
  ((SELECT id from client_details where client_id = 'patient_data_manager'), 'http://localhost:8096'),
  ((SELECT id from client_details where client_id = 'patient_data_manager'), 'https://patient-data-manager-test.hspconsortium.org/app.html'),
  ((SELECT id from client_details where client_id = 'patient_data_manager'), 'https://patient-data-manager.hspconsortium.org/app.html');
