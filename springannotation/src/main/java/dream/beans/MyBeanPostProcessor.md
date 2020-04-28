Bean的实例化过程 + BeanPostProcessor的原理
---------------
       //创建一个新的IOC容器
       public AnnotationConfigApplicationContext(Class... annotatedClasses) {
             this();
             this.register(annotatedClasses);
             //刷新容器
             this.refresh();
         }
        //refresh()方法中：初始化所有剩下的单实例对象
         this.finishBeanFactoryInitialization(beanFactory);
        //finishBeanFactoryInitialization(beanFactory)中最后一步：初始化所有剩下的单实例对象
         beanFactory.preInstantiateSingletons();
    
        //preInstantiateSingletons()中最后一步获取实例：
        getBean()  ----->  doGetBean():
         // 1. 在 parentBeanFactory 中如果存在 beanName，则直接返回父容器里面的 bean
         // 2. 初始化当前 bean 依赖的 bean
         // 3. 创建 bean 的实例       
  `调用createBean(beanName, mbd, args)方法创建Bean`
  
        createBean(beanName, mbd, args):
        // BeanPostProcessor 可以创建一个代理 bean 返回
        // 执行 InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation  -- bean 实例化前置处理
        // 执行 InstantiationAwareBeanPostProcessor.postProcessAfterInitialization  -- bean 初始化后置处理
        -----> doCreateBean(final String beanName, final RootBeanDefinition mbd, final @Nullable Object[] args)
      
      
   ```java
   protected Object doCreateBean(final String beanName, final RootBeanDefinition mbd, final @Nullable Object[] args) throws BeanCreationException {
  
   BeanWrapper instanceWrapper = null;
    
   if (instanceWrapper == null) {
      // 创建 bean 的实例：通过默认构造函数反射生成、通过配置的构造函数生成（构造方法注入）、通过 factoryMethod 生成
      // 最终会生成一个 BeanWrapper
             instanceWrapper = createBeanInstance(beanName, mbd, args);
         }
         final Object bean = instanceWrapper.getWrappedInstance();
         Class<?> beanType = instanceWrapper.getWrappedClass();
    
   // Allow post-processors to modify the merged bean definition.
   // 允许 post-processors 修改 bean 的定义
          synchronized (mbd.postProcessingLock) {
             if (!mbd.postProcessed) {
                 // 执行 MergedBeanDefinitionPostProcessor.postProcessMergedBeanDefinition,遍历得到容器中所有的BeanPostProcessor并执行
                 applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName);
                 mbd.postProcessed = true;
             }
         }
    
   // Eagerly cache singletons to be able to resolve circular references
   // even when triggered by lifecycle interfaces like BeanFactoryAware.
         boolean earlySingletonExposure = (mbd.isSingleton() && this.allowCircularReferences && isSingletonCurrentlyInCreation(beanName));
         if (earlySingletonExposure) {
             // 解决循环依赖相关
             addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, mbd, bean));
         }
    
   // Initialize the bean instance.
         Object exposedObject = bean;
         try {
             // 填充 bean 属性，处理属性注入：@Value, @Autowired, @Resource 等
             populateBean(beanName, mbd, instanceWrapper);
             // 初始化 bean：执行 initializeBean 方法 --> applyBeanPostProcessorsBeforeInitialization() --> 初始化方法invokeInitMethods(beanName, wrappedBean, mbd) --> applyBeanPostProcessorsAfterInitialization()
             //在initializeBean(beanName, exposedObject, mbd)中调用invokeInitMethods(beanName, wrappedBean, mbd)进行初始化
             exposedObject = initializeBean(beanName, exposedObject, mbd);
         } catch (Throwable ex) {
             if (ex instanceof BeanCreationException && beanName.equals(((BeanCreationException) ex).getBeanName())) {
                 throw (BeanCreationException) ex;
             }
             else {
                 throw new BeanCreationException(mbd.getResourceDescription(), beanName, "Initialization of bean failed", ex);
             }
         }
    
         ......
    
        return exposedObject;
   }
   ```
    
    
    
    
    
  
