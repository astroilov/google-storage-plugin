/*
 * Copyright 2013 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.jenkins.plugins.storage;

import java.security.GeneralSecurityException;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.jvnet.hudson.test.JenkinsRule;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.jenkins.plugins.credentials.oauth.GoogleOAuth2ScopeRequirement;
import com.google.jenkins.plugins.credentials.oauth.GoogleRobotCredentials;

/**
 * Tests for {@link UploadModule}.
 */
public class UploadModuleTest {

  @Rule
  public JenkinsRule jenkins = new JenkinsRule();

  @Rule public ExpectedException thrown = ExpectedException.none();

  @Mock private GoogleRobotCredentials mockGoogleRobotCredentials;
  private UploadModule underTest;

  @SuppressWarnings("serial")
  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    underTest = new UploadModule();
  }

  @Test
  public void newUploader_notRightScope()
      throws GeneralSecurityException, UploadException {
    GeneralSecurityException ex = new GeneralSecurityException();
    when(mockGoogleRobotCredentials.getGoogleCredential(isA(
        GoogleOAuth2ScopeRequirement.class)))
        .thenThrow(ex);
    thrown.expect(UploadException.class);
    thrown.expectMessage(
        Messages.UploadModule_ExceptionStorageService());
    underTest.getStorageService(mockGoogleRobotCredentials);
  }
}
