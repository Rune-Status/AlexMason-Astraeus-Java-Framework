package astraeus.plugins.dialogue

import java.util.function.Consumer

/**
 * The chain-able interface that allows implementing dialogue factories the ability to chain together.
 *
 * @author Seven
 */
interface Chainable: Consumer<DialogueFactory>