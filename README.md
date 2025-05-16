# Conversor de Monedas 💰

Conversor de divisas con interfaz de consola desarrollado en Java para el Challenge ONE.

## 🚀 Características

- Conversión entre 6 divisas principales
- Consulta de tasas en tiempo real (API)
- Interfaz interactiva por consola
- Validación de entradas
- Manejo de errores

## 📋 Divisas Disponibles

| Código | Nombre                  |
|--------|-------------------------|
| USD    | Dólar Estadounidense    |
| ARS    | Peso Argentino          |
| BOB    | Boliviano Boliviano     |
| BRL    | Real Brasileño          |
| CLP    | Peso Chileno            |
| COP    | Peso Colombiano         |

## ⚙️ Requisitos

- Java JDK 11+
- Maven 3.8+
- API Key de [ExchangeRate-API](https://www.exchangerate-api.com/)

## 📥 Instalación

1. Clonar repositorio:
```bash
git clone https://github.com/tu-usuario/conversor-moneda.git
cd conversor-moneda
```
2. Compilar y ejecutar:
```bash
mvn clean compile exec:java
```
## 🖥️ Cómo Usar
Seleccione moneda base (opciones 1-6)

Elija moneda objetivo

Ingrese la cantidad a convertir

Vea el resultado:

100.00 USD = 415.000,00 COP
Tasa: 1 USD = 4.150,0000 COP

## 🔧 Configurar API Key
Editar ConversorMoneda.java

Buscar: f2d5af2d3c0f8a1f91488c06

Reemplazar con tu API Key

## 📄 Licencia
MIT License - Ver LICENSE para más detalles.

Desarrollado por Ing Luis Angel Montelongo 👨💻
📧 Contacto: spacex.0002@gmail.com
