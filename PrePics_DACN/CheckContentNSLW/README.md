## Prerequisites

- **Python 3.11.4** (Download: [python.org](https://www.python.org/downloads/release/python-3114/))
- **pip** (should be installed with Python)
- **Virtual environment support** (built into Python 3.3+)

## Setup Instructions (VsCode)

### 1. Create and Activate Virtual Environment (rec)

#### On Windows:

```sh
python -m venv venv
venv\Scripts\activate
```

### 2. Install Required Libraries

```sh
pip install --upgrade pip
pip install -r requirements.txt
```

### 3. Run the API

```sh
python app.py
```

- **model.safetensors** is about 343Mb
- Ensure you are using Python **3.11.4**.
- Learn how to work with [Python's venv](https://www.freecodecamp.org/news/how-to-setup-virtual-environments-in-python/)
