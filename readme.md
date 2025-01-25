# java-libreoffice-server

A lightweight and efficient file conversion tool built with Java, TIO-boot, JODConverter, and LibreOffice. This tool allows users to convert various Office document formats to PDF through a simple HTTP API.

## Table of Contents

- [Technology Stack](#technology-stack)
- [Features](#features)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [API Documentation](#api-documentation)
  - [Endpoints](#endpoints)
  - [Request Parameters](#request-parameters)
  - [Response Format](#response-format)
- [API Examples](#api-examples)
  - [Using cURL](#using-curl)
  - [Using Postman](#using-postman)
- [Contributing](#contributing)
- [License](#license)

## Technology Stack

- **Java:** The primary programming language used for developing the application.
- **TIO-boot:** A lightweight and high-performance Java-based web server framework.
- **JODConverter:** Java OpenDocument Converter, a library that leverages LibreOffice for document conversions.
- **LibreOffice:** An open-source office suite used by JODConverter for converting various document formats to PDF.

## Features

- **Multi-format Support:** Convert a wide range of Office document formats including PPT/PPTX, DOC/DOCX, XLS/XLSX, ODT, ODS, and ODP to PDF.
- **Lightweight:** Minimal dependencies and efficient processing ensure fast and reliable conversions.
- **HTTP API:** Simple and straightforward API endpoints for easy integration with other applications or services.
- **Temporary File Management:** Automatically handles the creation and deletion of temporary files to ensure optimal performance and security.
- **Error Handling:** Provides meaningful error messages and status codes for better debugging and user experience.

## Installation

### Prerequisites

- **Java 8 or higher:** Ensure that Java is installed on your system. You can verify your Java version with:
  ```bash
  java -version
  ```
- **Maven:** Used for building the project. You can check if Maven is installed with:
  ```bash
  mvn -v
  ```

### Steps

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/litongjava/java-libreoffice-server.git
   cd java-libreoffice-server
   ```

2. **Build the Project:**
   ```bash
   mvn clean install
   ```

3. **Run the Application:**
   ```bash
   mvn spring-boot:run
   ```
   The server will start on `http://localhost:8080` by default.

## Configuration

The application uses default settings suitable for most environments. However, you can customize configurations such as server port, LibreOffice installation path, and more by modifying the `application.properties` file located in the `src/main/resources` directory.

Example `application.properties`:
```properties
server.port=8080
libreoffice.installation.path=/usr/lib/libreoffice
```

## Usage

Once the server is running, you can use the provided API endpoints to convert your Office documents to PDF. The API accepts multipart/form-data POST requests containing the file to be converted.

## API Documentation

### Endpoints

| Endpoint            | Method | Description                                 | Supported Formats |
|---------------------|--------|---------------------------------------------|--------------------|
| `/hello`            | GET    | A simple health check or greeting endpoint. | N/A                |
| `/api/ppt/pdf`      | POST   | Convert PPT/PPTX files to PDF.             | PPT, PPTX          |
| `/api/doc/pdf`      | POST   | Convert DOC/DOCX files to PDF.             | DOC, DOCX          |
| `/api/xls/pdf`      | POST   | Convert XLS/XLSX files to PDF.             | XLS, XLSX          |
| `/api/odt/pdf`      | POST   | Convert ODT files to PDF.                   | ODT                |
| `/api/ods/pdf`      | POST   | Convert ODS files to PDF.                   | ODS                |
| `/api/odp/pdf`      | POST   | Convert ODP files to PDF.                   | ODP                |
| `/api/convert/pdf`  | POST   | Convert multiple supported formats to PDF.  | DOC, DOCX, PPT, PPTX, XLS, XLSX, ODT, ODS, ODP |

### Request Parameters

All conversion endpoints (except `/hello`) accept a `multipart/form-data` POST request with the following parameter:

- **file** (required): The Office document file to be converted.

### Response Format

- **Success:** Returns the converted PDF file as a downloadable attachment with `Content-Type: application/pdf`.
- **Failure:** Returns a JSON response with an error message.

#### Success Response Example

- **Headers:**
  ```
  Content-Type: application/pdf
  Content-Disposition: attachment; filename="converted-file.pdf"
  ```
- **Body:** Binary content of the PDF file.

#### Error Response Example

- **Headers:**
  ```
  Content-Type: application/json
  ```
- **Body:**
  ```json
  {
    "status": "fail",
    "message": "Error message detailing the failure."
  }
  ```

## API Examples

### Using cURL

#### 1. Convert PPTX to PDF

```bash
curl -F "file=@1.pptx" http://127.0.0.1/api/ppt/pdf -o 1.pdf
```

**Explanation:**
- `-F "file=@1.pptx"`: Uploads the `1.pptx` file with the form field name `file`.
- `http://127.0.0.1/api/ppt/pdf`: API endpoint for converting PPT/PPTX files to PDF.
- `-o 1.pdf`: Saves the returned PDF as `1.pdf`.

---

#### 2. Convert DOCX to PDF

```bash
curl -F "file=@example.docx" http://127.0.0.1/api/doc/pdf -o example.pdf
```

**Explanation:**
- `-F "file=@example.docx"`: Uploads the `example.docx` file.
- `http://127.0.0.1/api/doc/pdf`: API endpoint for converting DOC/DOCX files to PDF.
- `-o example.pdf`: Saves the returned PDF as `example.pdf`.

---

#### 3. Convert XLSX to PDF

```bash
curl -F "file=@financial_report.xlsx" http://127.0.0.1/api/xls/pdf -o financial_report.pdf
```

**Explanation:**
- `-F "file=@financial_report.xlsx"`: Uploads the `financial_report.xlsx` file.
- `http://127.0.0.1/api/xls/pdf`: API endpoint for converting XLS/XLSX files to PDF.
- `-o financial_report.pdf`: Saves the returned PDF as `financial_report.pdf`.

---

#### 4. Convert ODT to PDF

```bash
curl -F "file=@example.odt" http://127.0.0.1/api/odt/pdf -o example.pdf
```

**Explanation:**
- `-F "file=@example.odt"`: Uploads the `example.odt` file.
- `http://127.0.0.1/api/odt/pdf`: API endpoint for converting ODT files to PDF.
- `-o example.pdf`: Saves the returned PDF as `example.pdf`.

---

#### 5. Convert Multiple Formats to PDF Using General Endpoint

```bash
curl -F "file=@presentation.pptx" http://127.0.0.1/api/convert/pdf -o presentation.pdf
```

**Explanation:**
- `-F "file=@presentation.pptx"`: Uploads the `presentation.pptx` file.
- `http://127.0.0.1/api/convert/pdf`: General API endpoint for converting multiple supported formats to PDF.
- `-o presentation.pdf`: Saves the returned PDF as `presentation.pdf`.

---

### Additional Notes

1. **File Paths:**
   - Ensure that the file paths provided to the `@` symbol are correct. If the files are in the current directory where you run the cURL command, you can simply use the filename as shown above.
   - For files located in different directories, provide the absolute or relative path. For example:
     ```bash
     curl -F "file=@/path/to/your/file/example.docx" http://127.0.0.1/api/doc/pdf -o example.pdf
     ```
     On Windows, you can use double backslashes or forward slashes:
     ```bash
     curl -F "file=@C:\\documents\\example.docx" http://127.0.0.1/api/doc/pdf -o example.pdf
     ```
     Or
     ```bash
     curl -F "file=@C:/documents/example.docx" http://127.0.0.1/api/doc/pdf -o example.pdf
     ```

2. **Output File Naming:**
   - The `-o` option allows you to specify the name of the output PDF file. You can name it as desired to reflect the source file or any other naming convention.

3. **Handling Spaces in Filenames:**
   - If your file names contain spaces, enclose the entire `@` path in quotes. For example:
     ```bash
     curl -F "file=@\"Chapter 1 Lecture Slides Set 1.pptx\"" http://127.0.0.1/api/ppt/pdf -o "Chapter1_Lecture_Slides_Set_1.pdf"
     ```

4. **Saving to Specific Directories:**
   - You can specify a directory path for the output file. For example:
     ```bash
     curl -F "file=@presentation.pptx" http://127.0.0.1/api/convert/pdf -o /path/to/save/presentation.pdf
     ```
     On Windows:
     ```bash
     curl -F "file=@presentation.pptx" http://127.0.0.1/api/convert/pdf -o "C:/Users/YourName/Documents/presentation.pdf"
     ```

5. **Automating Multiple Conversions:**
   - If you need to convert multiple files in one go, consider using a loop in your shell or writing a simple script. For example, in a Unix-like shell:
     ```bash
     for file in *.pptx *.docx *.xlsx *.odt; do
         base=$(basename "$file" | cut -d. -f1)
         curl -F "file=@$file" http://127.0.0.1/api/convert/pdf -o "${base}.pdf"
     done
     ```

6. **Error Handling:**
   - To handle errors and see more verbose output, you can add the `-v` flag:
     ```bash
     curl -v -F "file=@example.docx" http://127.0.0.1/api/doc/pdf -o example.pdf
     ```

7. **Redirecting Output and Errors:**
   - You can redirect standard error to a log file while saving the PDF:
     ```bash
     curl -F "file=@example.docx" http://127.0.0.1/api/doc/pdf -o example.pdf 2>error.log
     ```


### Using Postman

1. **Open Postman** and create a new `POST` request.
2. **Set the URL** to one of the conversion endpoints, for example: `http://127.0.0.1/api/ppt/pdf`.
3. **Navigate to the `Body` tab**, select `form-data`.
4. **Add a key** named `file`, set the type to `File`, and **upload your Office document**.
5. **Send the request**.
6. **Download the PDF** from the response.

## Contributing

Contributions are welcome! Please follow these steps:

1. **Fork the Repository**
2. **Create a Feature Branch**
   ```bash
   git checkout -b feature/YourFeature
   ```
3. **Commit Your Changes**
   ```bash
   git commit -m "Add your feature"
   ```
4. **Push to the Branch**
   ```bash
   git push origin feature/YourFeature
   ```
5. **Open a Pull Request**

Please ensure that your code follows the project's coding standards and includes appropriate tests.

## License

This project is licensed under the [MIT License](LICENSE).
---