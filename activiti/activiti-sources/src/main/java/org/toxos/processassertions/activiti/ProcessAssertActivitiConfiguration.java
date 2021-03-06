package org.toxos.processassertions.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.test.ActivitiRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toxos.processassertions.api.DefaultProcessAssertConfiguration;
import org.toxos.processassertions.api.SupportedLocale;
import org.toxos.processassertions.api.internal.AssertFactory;
import org.toxos.processassertions.api.internal.MessageLogger;
import org.toxos.processassertions.api.internal.Validate;

/**
 * Configuration for Process Assertions with the Activiti Process Engine.
 *
 * @author Tiese Barrell
 */
public class ProcessAssertActivitiConfiguration extends DefaultProcessAssertConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessAssertActivitiConfiguration.class);

    private static final String LOG_MESSAGES_BUNDLE_NAME = "org.toxos.processassertions.activiti.messages.LogMessages";

    private ProcessEngine processEngine;

    private AssertFactory assertFactory;

    private MessageLogger messageLogger;

    public ProcessAssertActivitiConfiguration(final ProcessEngine processEngine) {
        super();
        Validate.notNull(processEngine);
        this.processEngine = processEngine;
        initializeConfiguration();
    }

    public static final ProcessAssertActivitiConfiguration from(final ProcessEngine processEngine) {
        return new ProcessAssertActivitiConfiguration(processEngine);
    }

    public ProcessAssertActivitiConfiguration(final ActivitiRule activitiRule) {
        super();
        Validate.notNull(activitiRule);
        this.processEngine = activitiRule.getProcessEngine();
        initializeConfiguration();
    }

    public static final ProcessAssertActivitiConfiguration from(final ActivitiRule activitiRule) {
        return new ProcessAssertActivitiConfiguration(activitiRule);
    }

    public ProcessAssertActivitiConfiguration(final SupportedLocale supportedLocale, final ProcessEngine processEngine) {
        super(supportedLocale);
        Validate.notNull(processEngine);
        this.processEngine = processEngine;
        initializeConfiguration();
    }

    public static final ProcessAssertActivitiConfiguration from(final SupportedLocale supportedLocale, final ProcessEngine processEngine) {
        return new ProcessAssertActivitiConfiguration(supportedLocale, processEngine);
    }

    public ProcessAssertActivitiConfiguration(final SupportedLocale supportedLocale, final ActivitiRule activitiRule) {
        super(supportedLocale);
        Validate.notNull(activitiRule);
        this.processEngine = activitiRule.getProcessEngine();
        initializeConfiguration();
    }

    public static final ProcessAssertActivitiConfiguration from(final SupportedLocale supportedLocale, final ActivitiRule activitiRule) {
        return new ProcessAssertActivitiConfiguration(supportedLocale, activitiRule);
    }


    public ProcessEngine getProcessEngine() {
        return processEngine;
    }

    public void setProcessEngine(final ProcessEngine processEngine) {
        Validate.notNull(processEngine);
        this.processEngine = processEngine;
        initializeConfiguration();
    }

    @Override
    public AssertFactory getAssertFactory() {
        return assertFactory;
    }

    /**
     *
     * @param activitiRule
     */
    public void setActivitiRule(final ActivitiRule activitiRule) {
        Validate.notNull(activitiRule);
        setProcessEngine(activitiRule.getProcessEngine());
    }

    HistoryLevel getConfiguredHistoryLevel() {
        return doGetProcessEngineConfiguration().getHistoryLevel();
    }

    void deInitialize() {
        this.processEngine = null;
    }

    private void initializeConfiguration() {
        this.assertFactory = new AssertFactoryImpl(this);

        this.messageLogger = new MessageLogger(LOG_MESSAGES_BUNDLE_NAME, getLocale());

        if (this.processEngine == null) {
            this.processEngine = ProcessEngines.getDefaultProcessEngine();
        }
        registerProcessEngineCloseListener();

        this.messageLogger.logInfo(LOGGER, LogMessage.CONFIGURATION_1.getBundleKey(), this.processEngine.getName());
    }

    private void registerProcessEngineCloseListener() {
        doGetProcessEngineConfiguration().setProcessEngineLifecycleListener(new ProcessEngineCloseListener(this, messageLogger));
    }

    private ProcessEngineConfigurationImpl doGetProcessEngineConfiguration() {
        ProcessEngineConfigurationImpl configuration = null;
        if (this.processEngine instanceof ProcessEngineImpl) {
            configuration = ((ProcessEngineImpl) this.processEngine).getProcessEngineConfiguration();
        }
        return configuration;
    }

}
