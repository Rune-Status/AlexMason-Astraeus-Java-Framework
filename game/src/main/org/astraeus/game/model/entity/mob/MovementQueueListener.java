package astraeus.game.model.entity.mob;

import astraeus.game.model.World;
import astraeus.game.task.EventListener;

import java.util.Objects;
import java.util.Optional;

/**
 * The container class that holds the movement queue listener. The listener
 * allows for various actions to be appended to the end of the movement queue,
 * this is useful for things such as "walking to actions".
 *
 * @author lare96 <http://github.com/lare96>
 */
public final class MovementQueueListener {

    /**
     * The character this listener is dedicated to.
     */
    private final Mob mob;

    /**
     * The listener being used to execute tasks.
     */
    private Optional<MovementQueueListenerTask> listener = Optional.empty();

    /**
     * Creates a new {@link MovementQueueListener}.
     *
     * @param mob
     *            the mob this listener is dedicated to.
     */
    public MovementQueueListener(Mob mob) {
        this.mob = mob;
    }

    /**
     * Resets this {@link EventListener} so it may listen for the walking queue
     * to finish. Once the walking queue is finished the listener will run the
     * logic within {@code task}.
     * <p>
     * <p>
     * Please note that appended tasks are not guaranteed to be ran! If a new
     * task is being appended while the listener is already waiting to run
     * another task, the existing listener is stopped, the old task discarded,
     * and a new listener is started to run the new task.
     *
     * @param task
     *            the task that will be ran once the walking queue is finished.
     */
    public void append(Runnable task) {
        listener.ifPresent(t -> t.cancel());
        listener = Optional.of(new MovementQueueListenerTask(mob, task));
        mob.setFollowing(false);
        World.WORLD.submit(listener.get());
    }

    /**
     * The action listener implementation that allows for a task to be appended
     * to the end of the movement queue.
     *
     * @author lare96 <http://github.com/lare96>
     */
    private static final class MovementQueueListenerTask extends EventListener {

        /**
         * The character that the queued task will be ran for.
         */
        private final Mob mob;

        /**
         * The queued task that will be executed by this listener.
         */
        private final Runnable task;

        /**
         * Creates a new {@link MovementQueueListenerTask}.
         *
         * @param mob
         *            the mob that the queued task will be ran for.
         * @param task
         *            the queued task that will be executed by this listener.
         */
        public MovementQueueListenerTask(Mob mob, Runnable task) {
            this.mob = mob;
            this.task = Objects.requireNonNull(task);
        }

        @Override
        public void run() {
            if (mob.isRegistered()) {
                try {
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
