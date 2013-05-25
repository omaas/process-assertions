/*******************************************************************************
 * Copyright 2013 Tiese Barrell
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.mazidea.activiti.assertion;

import java.util.ResourceBundle;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract base class for process assertions. Provides easy access to logger
 * for printing messages.
 * 
 * @author Tiese Barrell
 * 
 */
public abstract class AbstractProcessAssert {

  // Log to the ProcessAssert class' logger
  private static final Logger LOGGER = LoggerFactory.getLogger(ProcessAssert.class);

  private static ResourceBundle bundle;

  protected AbstractProcessAssert() {
    super();
  }

  /**
   * Logs the message and parameters at trace level if the logger has enabled
   * ERROR level.
   * 
   * @param message
   *          the log message
   * @param objects
   *          the parameters for substitution
   */
  protected static void error(final LogMessage message, final Object... objects) {
    if (LOGGER.isErrorEnabled()) {
      LOGGER.error(formatLogMessage(message, objects));
    }
  }

  /**
   * Logs the message and parameters at debug level if the logger has enabled
   * DEBUG level.
   * 
   * @param message
   *          the log message
   * @param objects
   *          the parameters for substitution
   */
  protected static void debug(final LogMessage message, final Object... objects) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(formatLogMessage(message, objects));
    }
  }

  /**
   * Logs the message and parameters at trace level if the logger has enabled
   * TRACE level.
   * 
   * @param message
   *          the log message
   * @param objects
   *          the parameters for substitution
   */
  protected static void trace(final LogMessage message, final Object... objects) {
    if (LOGGER.isTraceEnabled()) {
      LOGGER.trace(formatLogMessage(message, objects));
    }
  }

  /**
   * Fails the assertions by throwing an AssertionError with the provided
   * message and parameters.
   * 
   * Uses {@link String#format(String, Object...)} to format messages.
   * 
   * @param message
   *          the log message
   * @param objects
   *          the parameters for substitution
   */
  protected static void fail(final LogMessage message, final Object... objects) {
    final String formattedMessage = formatLogMessage(message, objects);
    error(LogMessage.ERROR_ASSERTIONS_1, formattedMessage);
    Assert.fail(formattedMessage);
  }

  private static String formatLogMessage(final LogMessage message, final Object... objects) {
    return String.format(getMessage(message), objects);
  }

  private static String getMessage(LogMessage message) {
    return getBundle().getString(message.getBundleKey());
  }

  private static ResourceBundle getBundle() {
    if (bundle == null) {
      bundle = ResourceBundle.getBundle("messages.LogMessages");
    }
    return bundle;
  }
}