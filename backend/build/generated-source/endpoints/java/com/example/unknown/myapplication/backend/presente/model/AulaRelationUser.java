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
 * Model definition for AulaRelationUser.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the presente. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class AulaRelationUser extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long aluno;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long aula;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String composite;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime date;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean presente;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getAluno() {
    return aluno;
  }

  /**
   * @param aluno aluno or {@code null} for none
   */
  public AulaRelationUser setAluno(java.lang.Long aluno) {
    this.aluno = aluno;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getAula() {
    return aula;
  }

  /**
   * @param aula aula or {@code null} for none
   */
  public AulaRelationUser setAula(java.lang.Long aula) {
    this.aula = aula;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getComposite() {
    return composite;
  }

  /**
   * @param composite composite or {@code null} for none
   */
  public AulaRelationUser setComposite(java.lang.String composite) {
    this.composite = composite;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getDate() {
    return date;
  }

  /**
   * @param date date or {@code null} for none
   */
  public AulaRelationUser setDate(com.google.api.client.util.DateTime date) {
    this.date = date;
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
  public AulaRelationUser setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getPresente() {
    return presente;
  }

  /**
   * @param presente presente or {@code null} for none
   */
  public AulaRelationUser setPresente(java.lang.Boolean presente) {
    this.presente = presente;
    return this;
  }

  @Override
  public AulaRelationUser set(String fieldName, Object value) {
    return (AulaRelationUser) super.set(fieldName, value);
  }

  @Override
  public AulaRelationUser clone() {
    return (AulaRelationUser) super.clone();
  }

}
