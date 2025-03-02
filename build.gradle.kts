plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.docx4j:docx4j:6.1.2")

    // Зависимости для JAXB (необходимы для Java 11+)
    implementation("javax.xml.bind:jaxb-api:2.3.1")
    implementation("org.glassfish.jaxb:jaxb-runtime:2.3.3")

    // MySQL connector
    implementation("mysql:mysql-connector-java:8.0.33")

    implementation("org.apache.pdfbox:pdfbox:2.0.33") // PDFBox
}

tasks.test {
    useJUnitPlatform()
}