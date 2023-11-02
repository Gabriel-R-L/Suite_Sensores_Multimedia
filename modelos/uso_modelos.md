# from_dataset_model.pkl
- Así se carga el modelo en python, buscar la versión en java
- Al modelo se le pasa el tablero, cambiando:
	- 'x' por 2.0
	- 'o' por 1.0
	- ' ' por 0.0
- modelo_cargado = joblib.load('from_dataset_model.pkl')