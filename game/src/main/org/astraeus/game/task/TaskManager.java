package astraeus.game.task;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.logging.Logger;

import astraeus.util.LoggerUtils;

/**
 * Handles the processing and execution of {@link Task}s. Functions contained within this class should only be invoked on the
 * {@link GameService} thread to ensure thread safety.
 *
 * @author lare96 <http://github.org/lare96>
 */
public final class TaskManager {

    /**
     * The single logger for this class.
     */
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerUtils.getLogger(TaskManager.class);    

    /**
     * A {@link List} of tasks that have been submitted and are awaiting execution.
     */
    private final List<Task> awaitingExecution = new LinkedList<>();

    /**
     * A {@link Queue} of tasks that are ready to be executed.
     */
    private final Queue<Task> executionQueue = new ArrayDeque<>();

    /**
     * Schedules {@code t} to run in the underlying {@code TaskManager}.
     *
     * @param t The {@link Task} to schedule.
     */
    public void schedule(Task t) {
        t.onSchedule();
        if (t.isInstant()) {
            try {
                t.execute();
            } catch (Exception e) {
                t.onException(e);
                e.printStackTrace();
            }
        }
        awaitingExecution.add(t);
    }

    /**
     * Runs an iteration of the {@link Task} processing logic. All {@link Exception}s thrown by {@code Task}s are caught and
     * logged by the underlying {@link Logger}.
     */
    public void runTaskIteration() {
        Iterator<Task> $it = awaitingExecution.iterator();
        while ($it.hasNext()) {
            Task it = $it.next();

            if (!it.isRunning()) {
                $it.remove();
                continue;
            }
            it.onLoop();
            if (it.canExecute()) {
                executionQueue.add(it);
            }
        }

        for (; ; ) {
            Task it = executionQueue.poll();
            if (it == null) {
                break;
            }

            try {
                it.execute();
            } catch (Exception e) {
                it.onException(e);
                e.printStackTrace();
            }
        }
    }

    /**
     * Iterates through all active {@link Task}s and cancels all that have {@code attachment} as their attachment.
     */
    public void cancel(Object attachment) {
        awaitingExecution.stream().filter(it -> Objects.equals(attachment, it.getAttachment().orElse(null)))
            .forEach(Task::cancel);
    }
}