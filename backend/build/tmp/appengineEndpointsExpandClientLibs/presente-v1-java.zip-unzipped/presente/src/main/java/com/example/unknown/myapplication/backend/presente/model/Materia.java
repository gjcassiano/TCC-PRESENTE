/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2017-11-07 19:12:12 UTC)
 * on 2017-12-08 at 21:17:06 UTC 
 * Modify at your own risk.
 */

package com.example.unknown.myapplication.backend.presente.model;

/**
 * Model definition for Materia.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the presente. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Materia extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.util.List<java.lang.Long> alunos;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String nome;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long professor;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String sigla;

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.Long> getAlunos() {
    return alunos;
  }

  /**
   * @param alunos alunos or {@code null} for none
   */
  public Materia setAlunos(java.util.List<java.lang.Long> alunos) {
    this.alunos = alunos;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public Materia setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getNome() {
    return nome;
  }

  /**
   * @param nome nome or {@code null} for none
   */
  public Materia setNome(java.lang.String nome) {
    this.nome = nome;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getProfessor() {
    return professor;
  }

  /**
   * @param professor professor or {@code null} for none
   */
  public Materia setProfessor(java.lang.Long professor) {
    this.professor = professor;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getSigla() {
    return sigla;
  }

  /**
   * @param sigla sigla or {@code null} for none
   */
  public Materia setSigla(java.lang.String sigla) {
    this.sigla = sigla;
    return this;
  }

  @Override
  public Materia set(String fieldName, Object value) {
    return (Materia) super.set(fieldName, value);
  }

  @Override
  public Materia clone() {
    return (Materia) super.clone();
  }

}
