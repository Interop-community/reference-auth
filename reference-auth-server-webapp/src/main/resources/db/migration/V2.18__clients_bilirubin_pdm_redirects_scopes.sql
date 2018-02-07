INSERT INTO client_redirect_uri (owner_id, redirect_uri) VALUES
  ((SELECT id from client_details where client_id = 'patient_data_manager'), 'http://localhost:8096/app.html'),
  ((SELECT id from client_details where client_id = 'bilirubin_chart'), 'http://localhost:8086/app.html');

DELETE FROM client_scope WHERE owner_id = (SELECT id from client_details where client_id = 'patient_data_manager')
    AND scope = 'patient/*.*';

INSERT INTO client_scope (owner_id, scope) VALUES
  ((SELECT id from client_details where client_id = 'patient_data_manager'), 'patient/*.read'),
  ((SELECT id from client_details where client_id = 'patient_data_manager'), 'patient/*.write');