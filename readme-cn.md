# java-libreoffice-server

一个基于 Java、TIO-boot、JODConverter 和 LibreOffice 构建的轻量级高效文件转换工具。该工具允许用户通过简单的 HTTP API 将各种 Office 文档格式转换为 PDF。

## 目录

- [技术栈](#技术栈)
- [功能特性](#功能特性)
- [安装](#安装)
- [配置](#配置)
- [使用方法](#使用方法)
- [API 文档](#api文档)
  - [端点](#端点)
  - [请求参数](#请求参数)
  - [响应格式](#响应格式)
- [API 示例](#api示例)
  - [使用 cURL](#使用-curl)
  - [使用 Postman](#使用-postman)
- [贡献指南](#贡献指南)
- [许可证](#许可证)

## 技术栈

- **Java：** 用于开发应用程序的主要编程语言。
- **TIO-boot：** 一个轻量级高性能的基于 Java 的 Web 服务器框架。
- **JODConverter：** Java OpenDocument Converter，利用 LibreOffice 进行文档转换的库。
- **LibreOffice：** 一个开源办公套件，JODConverter 使用它来将各种文档格式转换为 PDF。

## 功能特性

- **多格式支持：** 支持将包括 PPT/PPTX、DOC/DOCX、XLS/XLSX、ODT、ODS 和 ODP 在内的多种 Office 文档格式转换为 PDF。
- **轻量级：** 最小化的依赖和高效的处理确保转换快速可靠。
- **HTTP API：** 简单直接的 API 端点，便于与其他应用程序或服务集成。
- **临时文件管理：** 自动处理临时文件的创建和删除，确保最佳性能和安全性。
- **错误处理：** 提供有意义的错误消息和状态码，便于调试和提升用户体验。

## 安装

### 前置条件

- **Java 8 或更高版本：** 确保系统上已安装 Java。可以通过以下命令验证 Java 版本：
  ```bash
  java -version
  ```
- **Maven：** 用于构建项目。可以通过以下命令检查是否已安装 Maven：
  ```bash
  mvn -v
  ```

### 步骤

1. **克隆仓库：**
   ```bash
   git clone https://github.com/litongjava/java-libreoffice-server.git
   cd java-libreoffice-server
   ```

2. **构建项目：**
   ```bash
   mvn clean install
   ```

3. **运行应用程序：**
   ```bash
   mvn spring-boot:run
   ```
   服务器将默认在 `http://localhost:8080` 启动。

## 配置

应用程序使用适用于大多数环境的默认设置。不过，您可以通过修改位于 `src/main/resources` 目录下的 `application.properties` 文件来自定义配置，例如服务器端口、LibreOffice 安装路径等。

示例 `application.properties`：
```properties
server.port=8080
libreoffice.installation.path=/usr/lib/libreoffice
```

## 使用方法

服务器运行后，您可以使用提供的 API 端点将 Office 文档转换为 PDF。API 接受包含要转换文件的 `multipart/form-data` POST 请求。

## API 文档

### 端点

| 端点               | 方法 | 描述                                   | 支持格式                             |
|--------------------|------|----------------------------------------|--------------------------------------|
| `/hello`           | GET  | 简单的健康检查或问候端点。             | 不适用                               |
| `/api/ppt/pdf`     | POST | 将 PPT/PPTX 文件转换为 PDF。           | PPT, PPTX                            |
| `/api/doc/pdf`     | POST | 将 DOC/DOCX 文件转换为 PDF。           | DOC, DOCX                            |
| `/api/xls/pdf`     | POST | 将 XLS/XLSX 文件转换为 PDF。           | XLS, XLSX                            |
| `/api/odt/pdf`     | POST | 将 ODT 文件转换为 PDF。                 | ODT                                  |
| `/api/ods/pdf`     | POST | 将 ODS 文件转换为 PDF。                 | ODS                                  |
| `/api/odp/pdf`     | POST | 将 ODP 文件转换为 PDF。                 | ODP                                  |
| `/api/convert/pdf` | POST | 将多种支持的格式转换为 PDF。            | DOC, DOCX, PPT, PPTX, XLS, XLSX, ODT, ODS, ODP |

### 请求参数

所有转换端点（除了 `/hello`）接受一个 `multipart/form-data` POST 请求，包含以下参数：

- **file**（必需）：要转换的 Office 文档文件。

### 响应格式

- **成功：** 返回转换后的 PDF 文件作为可下载附件，`Content-Type` 为 `application/pdf`。
- **失败：** 返回包含错误消息的 JSON 响应。

#### 成功响应示例

- **头部：**
  ```
  Content-Type: application/pdf
  Content-Disposition: attachment; filename="converted-file.pdf"
  ```
- **正文：** PDF 文件的二进制内容。

#### 错误响应示例

- **头部：**
  ```
  Content-Type: application/json
  ```
- **正文：**
  ```json
  {
    "status": "fail",
    "message": "详细说明失败原因的错误消息。"
  }
  ```

## API 示例

### 使用 cURL

#### 1. 将 PPTX 转换为 PDF

```bash
curl -F "file=@1.pptx" http://127.0.0.1/api/ppt/pdf -o 1.pdf
```

**解释：**
- `-F "file=@1.pptx"`：上传名为 `1.pptx` 的文件，表单字段名为 `file`。
- `http://127.0.0.1/api/ppt/pdf`：用于将 PPT/PPTX 文件转换为 PDF 的 API 端点。
- `-o 1.pdf`：将返回的 PDF 保存为 `1.pdf`。

---

#### 2. 将 DOCX 转换为 PDF

```bash
curl -F "file=@example.docx" http://127.0.0.1/api/doc/pdf -o example.pdf
```

**解释：**
- `-F "file=@example.docx"`：上传名为 `example.docx` 的文件。
- `http://127.0.0.1/api/doc/pdf`：用于将 DOC/DOCX 文件转换为 PDF 的 API 端点。
- `-o example.pdf`：将返回的 PDF 保存为 `example.pdf`。

---

#### 3. 将 XLSX 转换为 PDF

```bash
curl -F "file=@financial_report.xlsx" http://127.0.0.1/api/xls/pdf -o financial_report.pdf
```

**解释：**
- `-F "file=@financial_report.xlsx"`：上传名为 `financial_report.xlsx` 的文件。
- `http://127.0.0.1/api/xls/pdf`：用于将 XLS/XLSX 文件转换为 PDF 的 API 端点。
- `-o financial_report.pdf`：将返回的 PDF 保存为 `financial_report.pdf`。

---

#### 4. 将 ODT 转换为 PDF

```bash
curl -F "file=@example.odt" http://127.0.0.1/api/odt/pdf -o example.pdf
```

**解释：**
- `-F "file=@example.odt"`：上传名为 `example.odt` 的文件。
- `http://127.0.0.1/api/odt/pdf`：用于将 ODT 文件转换为 PDF 的 API 端点。
- `-o example.pdf`：将返回的 PDF 保存为 `example.pdf`。

---

#### 5. 使用通用端点将多种格式转换为 PDF

```bash
curl -F "file=@presentation.pptx" http://127.0.0.1/api/convert/pdf -o presentation.pdf
```

**解释：**
- `-F "file=@presentation.pptx"`：上传名为 `presentation.pptx` 的文件。
- `http://127.0.0.1/api/convert/pdf`：用于将多种支持格式转换为 PDF 的通用 API 端点。
- `-o presentation.pdf`：将返回的 PDF 保存为 `presentation.pdf`。

---

### 其他注意事项

1. **文件路径：**
   - 确保提供给 `@` 符号的文件路径是正确的。如果文件位于运行 cURL 命令的当前目录，可以像上面示例那样直接使用文件名。
   - 对于位于不同目录的文件，请提供绝对路径或相对路径。例如：
     ```bash
     curl -F "file=@/path/to/your/file/example.docx" http://127.0.0.1/api/doc/pdf -o example.pdf
     ```
     在 Windows 上，您可以使用双反斜杠或正斜杠：
     ```bash
     curl -F "file=@C:\\documents\\example.docx" http://127.0.0.1/api/doc/pdf -o example.pdf
     ```
     或
     ```bash
     curl -F "file=@C:/documents/example.docx" http://127.0.0.1/api/doc/pdf -o example.pdf
     ```

2. **输出文件命名：**
   - `-o` 选项允许您指定输出 PDF 文件的名称。您可以根据需要命名，以反映源文件或任何其他命名约定。

3. **处理文件名中的空格：**
   - 如果文件名包含空格，请将整个 `@` 路径用引号括起来。例如：
     ```bash
     curl -F "file=@\"Chapter 1 Lecture Slides Set 1.pptx\"" http://127.0.0.1/api/ppt/pdf -o "Chapter1_Lecture_Slides_Set_1.pdf"
     ```

4. **保存到特定目录：**
   - 您可以为输出文件指定目录路径。例如：
     ```bash
     curl -F "file=@presentation.pptx" http://127.0.0.1/api/convert/pdf -o /path/to/save/presentation.pdf
     ```
     在 Windows 上：
     ```bash
     curl -F "file=@presentation.pptx" http://127.0.0.1/api/convert/pdf -o "C:/Users/YourName/Documents/presentation.pdf"
     ```

5. **自动化多文件转换：**
   - 如果需要一次转换多个文件，可以考虑在 shell 中使用循环或编写简单的脚本。例如，在类 Unix shell 中：
     ```bash
     for file in *.pptx *.docx *.xlsx *.odt; do
         base=$(basename "$file" | cut -d. -f1)
         curl -F "file=@$file" http://127.0.0.1/api/convert/pdf -o "${base}.pdf"
     done
     ```

6. **错误处理：**
   - 要处理错误并查看更详细的输出，可以添加 `-v` 标志：
     ```bash
     curl -v -F "file=@example.docx" http://127.0.0.1/api/doc/pdf -o example.pdf
     ```

7. **重定向输出和错误：**
   - 您可以在保存 PDF 的同时将标准错误重定向到日志文件：
     ```bash
     curl -F "file=@example.docx" http://127.0.0.1/api/doc/pdf -o example.pdf 2>error.log
     ```

### 使用 Postman

1. **打开 Postman** 并创建一个新的 `POST` 请求。
2. **设置 URL** 为其中一个转换端点，例如：`http://127.0.0.1/api/ppt/pdf`。
3. **导航到 `Body` 标签**，选择 `form-data`。
4. **添加一个键**，命名为 `file`，将类型设置为 `File`，并**上传您的 Office 文档**。
5. **发送请求**。
6. **从响应中下载 PDF**。
## python 调用示例
```
import requests

url = "https://java-libreoffice-server.fly.dev/api/ppt/pdf"
file_path = r'C:\Users\Administrator\Downloads\Accounting Basics.ppt'

with open(file_path, 'rb') as file:
    files = {
        'file': ('Accounting Basics.ppt', file, 'application/vnd.ms-powerpoint')
    }
    response = requests.post(url, files=files)

print(response.text)

```
## 贡献指南

欢迎贡献！请按照以下步骤操作：

1. **Fork 仓库**
2. **创建功能分支**
   ```bash
   git checkout -b feature/YourFeature
   ```
3. **提交您的更改**
   ```bash
   git commit -m "添加您的功能"
   ```
4. **推送到分支**
   ```bash
   git push origin feature/YourFeature
   ```
5. **打开 Pull Request**

请确保您的代码遵循项目的编码标准并包含适当的测试。

## 许可证

该项目基于 [MIT 许可证](LICENSE) 进行授权。