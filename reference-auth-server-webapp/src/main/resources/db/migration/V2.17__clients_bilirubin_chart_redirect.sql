INSERT INTO client_redirect_uri (owner_id, redirect_uri) VALUES
  ((SELECT id from client_details where client_id = 'bilirubin_chart'), 'http://localhost:8086'),
  ((SELECT id from client_details where client_id = 'bilirubin_chart'), 'https://bilirubin-risk-chart-test.logicahealth.org/app.html'),
  ((SELECT id from client_details where client_id = 'bilirubin_chart'), 'https://bilirubin-risk-chart.logicahealth.org/app.html');