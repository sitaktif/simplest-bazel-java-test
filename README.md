Simple repo for issue reproduction
==================================

These steps will run with Bazel 3.3.0.

### Local strategy not recognised

```
bazelisk test //src/bazeltest:JavaTest --test_strategy=local --strategy=TestRunner=worker --experimental_persistent_test_runner --nocache_test_results
```

Getting:

```
ERROR: No context of type TestActionContext registered for requested value 'local', available identifiers are: [standalone, exclusive]
```

### Replacing strategy with standalone fails with lack of proto output

[Docs for --spawn_strategy](https://docs.bazel.build/versions/master/user-manual.html#flag--spawn_strategy) suggest `standalone` is the same as `local` but when calling:

```
bazelisk test //src/bazeltest:JavaTest --test_strategy=standalone --strategy=TestRunner=worker --experimental_persistent_test_runner --nocache_test_results
```

...we're getting:

```
ERROR: /Users/rchossart/geo/test-bazel-persitent-exec/src/bazeltest/BUILD.bazel:6:10:  failed: Worker process returned an unparseable WorkResponse!

Did you try to print something to stdout? Workers aren't allowed to do this, as it breaks the protocol between Bazel and the worker process.

---8<---8<--- Exception details ---8<---8<---
com.google.protobuf.InvalidProtocolBufferException$InvalidWireTypeException: Protocol message tag had invalid wire type.
	at com.google.protobuf.InvalidProtocolBufferException.invalidWireType(InvalidProtocolBufferException.java:111)
	at com.google.protobuf.UnknownFieldSet$Builder.mergeFieldFrom(UnknownFieldSet.java:557)
	at com.google.protobuf.UnknownFieldSet$Builder.mergeFrom(UnknownFieldSet.java:521)
	at com.google.protobuf.UnknownFieldSet$Builder.mergeFrom(UnknownFieldSet.java:634)
	at com.google.protobuf.UnknownFieldSet$Builder.mergeFrom(UnknownFieldSet.java:308)
	at com.google.protobuf.CodedInputStream$StreamDecoder.readGroup(CodedInputStream.java:2311)
	at com.google.protobuf.UnknownFieldSet$Builder.mergeFieldFrom(UnknownFieldSet.java:548)
	at com.google.protobuf.UnknownFieldSet$Builder.mergeFrom(UnknownFieldSet.java:521)
	at com.google.protobuf.UnknownFieldSet$Builder.mergeFrom(UnknownFieldSet.java:634)
	at com.google.protobuf.UnknownFieldSet$Builder.mergeFrom(UnknownFieldSet.java:308)
	at com.google.protobuf.CodedInputStream$StreamDecoder.readGroup(CodedInputStream.java:2311)
	at com.google.protobuf.UnknownFieldSet$Builder.mergeFieldFrom(UnknownFieldSet.java:548)
	at com.google.protobuf.GeneratedMessageV3.parseUnknownField(GeneratedMessageV3.java:320)
	at com.google.devtools.build.lib.worker.WorkerProtocol$WorkResponse.<init>(WorkerProtocol.java:2112)
	at com.google.devtools.build.lib.worker.WorkerProtocol$WorkResponse.<init>(WorkerProtocol.java:2052)
	at com.google.devtools.build.lib.worker.WorkerProtocol$WorkResponse$1.parsePartialFrom(WorkerProtocol.java:2778)
	at com.google.devtools.build.lib.worker.WorkerProtocol$WorkResponse$1.parsePartialFrom(WorkerProtocol.java:2772)
	at com.google.protobuf.AbstractParser.parsePartialFrom(AbstractParser.java:215)
	at com.google.protobuf.AbstractParser.parsePartialDelimitedFrom(AbstractParser.java:255)
	at com.google.protobuf.AbstractParser.parseDelimitedFrom(AbstractParser.java:267)
	at com.google.protobuf.AbstractParser.parseDelimitedFrom(AbstractParser.java:272)
	at com.google.protobuf.AbstractParser.parseDelimitedFrom(AbstractParser.java:48)
	at com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(GeneratedMessageV3.java:375)
	at com.google.devtools.build.lib.worker.WorkerProtocol$WorkResponse.parseDelimitedFrom(WorkerProtocol.java:2351)
	at com.google.devtools.build.lib.worker.Worker.getResponse(Worker.java:136)
	at com.google.devtools.build.lib.worker.WorkerSpawnRunner.execInWorker(WorkerSpawnRunner.java:401)
	at com.google.devtools.build.lib.worker.WorkerSpawnRunner.actuallyExec(WorkerSpawnRunner.java:203)
	at com.google.devtools.build.lib.worker.WorkerSpawnRunner.exec(WorkerSpawnRunner.java:136)
	at com.google.devtools.build.lib.exec.SpawnRunner.execAsync(SpawnRunner.java:241)
	at com.google.devtools.build.lib.exec.AbstractSpawnStrategy.exec(AbstractSpawnStrategy.java:128)
	at com.google.devtools.build.lib.exec.AbstractSpawnStrategy.exec(AbstractSpawnStrategy.java:96)
	at com.google.devtools.build.lib.actions.SpawnStrategy.beginExecution(SpawnStrategy.java:39)
	at com.google.devtools.build.lib.exec.SpawnStrategyResolver.beginExecution(SpawnStrategyResolver.java:62)
	at com.google.devtools.build.lib.exec.StandaloneTestStrategy.beginTestAttempt(StandaloneTestStrategy.java:308)
	at com.google.devtools.build.lib.exec.StandaloneTestStrategy.access$200(StandaloneTestStrategy.java:69)
	at com.google.devtools.build.lib.exec.StandaloneTestStrategy$StandaloneTestRunnerSpawn.beginExecution(StandaloneTestStrategy.java:442)
	at com.google.devtools.build.lib.analysis.test.TestRunnerAction.beginIfNotCancelled(TestRunnerAction.java:834)
	at com.google.devtools.build.lib.analysis.test.TestRunnerAction.beginExecution(TestRunnerAction.java:803)
	at com.google.devtools.build.lib.analysis.test.TestRunnerAction.execute(TestRunnerAction.java:859)
	at com.google.devtools.build.lib.analysis.test.TestRunnerAction.execute(TestRunnerAction.java:850)
	at com.google.devtools.build.lib.skyframe.SkyframeActionExecutor$4.execute(SkyframeActionExecutor.java:779)
	at com.google.devtools.build.lib.skyframe.SkyframeActionExecutor$ActionRunner.continueAction(SkyframeActionExecutor.java:925)
	at com.google.devtools.build.lib.skyframe.SkyframeActionExecutor$ActionRunner.run(SkyframeActionExecutor.java:896)
	at com.google.devtools.build.lib.skyframe.ActionExecutionState.runStateMachine(ActionExecutionState.java:128)
	at com.google.devtools.build.lib.skyframe.ActionExecutionState.getResultOrDependOnFuture(ActionExecutionState.java:80)
	at com.google.devtools.build.lib.skyframe.SkyframeActionExecutor.executeAction(SkyframeActionExecutor.java:419)
	at com.google.devtools.build.lib.skyframe.ActionExecutionFunction.checkCacheAndExecuteIfNeeded(ActionExecutionFunction.java:897)
	at com.google.devtools.build.lib.skyframe.ActionExecutionFunction.compute(ActionExecutionFunction.java:300)
	at com.google.devtools.build.skyframe.AbstractParallelEvaluator$Evaluate.run(AbstractParallelEvaluator.java:438)
	at com.google.devtools.build.lib.concurrent.AbstractQueueVisitor$WrappedRunnable.run(AbstractQueueVisitor.java:398)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
	at java.base/java.lang.Thread.run(Thread.java:834)
---8<---8<--- End of exception details ---8<---8<---

---8<---8<--- Start of log ---8<---8<---
exec ${PAGER:-/usr/bin/less} "$0" || exit 1
Executing tests from //src/bazeltest:JavaTest
---8<---8<--- End of log ---8<---8<---
Target //src/bazeltest:JavaTest up-to-date:
  bazel-bin/src/bazeltest/JavaTest.jar
  bazel-bin/src/bazeltest/JavaTest
  bazel-bin/src/bazeltest/JavaTest_test_runtime_classpath.txt
```
