INSERT INTO client_redirect_uri (owner_id, redirect_uri) VALUES
  ((SELECT id from client_details where client_id = 'patient_data_manager'), 'http://localhost:8096'),
  ((SELECT id from client_details where client_id = 'patient_data_manager'), 'https://patient-data-manager-test.logicahealth.org/app.html'),
  ((SELECT id from client_details where client_id = 'patient_data_manager'), 'https://patient-data-manager.logicahealth.org/app.html');