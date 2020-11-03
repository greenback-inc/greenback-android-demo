# Greenback Demo for Android

![Greenback Logo](https://www.greenback.com/assets/f/blogs/github-greenback-java/greenback-logo-badge.png)

The official [Greenback](https://www.greenback.com) open source demo of the Greenback Kit on Android!

The [Greenback Platform](https://www.greenback.com/platform) consists of APIs to build modern applications with high-def itemized financial data.  You can read more about our REST-based APIs and data models on the [Greenback Developer Portal](https://developer.greenback.com).  This demo currently demonstrates the Vision API.

## Vision API

Advanced AI, OCR (Optical Character Recognition), and NLP (Natural Language Processing) to extract structured transaction data from real world photos and documents in near real-time. You can quickly and easily build web or mobile applications that can convert images (PNG/JPEG) or documents (Microsoft Word/HTML/PDF) that contain receipts, bills, invoices, statements, and other types into structured and annotated data.

![Greenback Kit on Android Demo](https://www.greenback.com/assets/f/blogs/github-greenback-java/greenback-vision-demo.gif)

## Setup

### Requirements

 - Android Studio (we suggest 4.1+)

### Build Configuration

Edit ~/.gradle/gradle.properties and setup your build configuration. These will then be compiled in when you build your project, and more securely let you try the demo with your Greenback access token.

```
GREENBACK_ENDPOINT="https://api.greenback.com"
GREENBACK_ACCESS_TOKEN="your-access-token"
```

## License

This project and source code is licensed with Apache License 2.0.

## Questions and Support

Please contact [Greenback Support or Sales](https://www.greenback.com/contact) if you have any additional questions.
