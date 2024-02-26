package com.rodrigor.java.exemplos.sca_interface;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GravadorXML implements GravadorAluno {

    private static final String XML_FILE = "alunos.xml";

    @Override
    public void salvarAluno(Aluno aluno) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document;
            Element root;

            File xmlFile = new File(XML_FILE);
            if (xmlFile.exists()) {
                document = documentBuilder.parse(xmlFile);
                root = document.getDocumentElement();
            } else {
                document = documentBuilder.newDocument();
                root = document.createElement("alunos");
                document.appendChild(root);
            }

            Element alunoElement = document.createElement("aluno");
            alunoElement.setAttribute("matricula", aluno.getMatricula());
            alunoElement.appendChild(document.createElement("nome")).appendChild(document.createTextNode(aluno.getNome()));

            root.appendChild(alunoElement);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Aluno> recuperarAlunos() {
        List<Aluno> alunos = new ArrayList<>();
        try {
            File xmlFile = new File(XML_FILE);
            if (!xmlFile.exists()) {
                return alunos;
            }

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("aluno");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String nome = element.getElementsByTagName("nome").item(0).getTextContent();
                    String matricula = element.getAttribute("matricula");
                    alunos.add(new Aluno(nome, matricula));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return alunos;
    }
}
