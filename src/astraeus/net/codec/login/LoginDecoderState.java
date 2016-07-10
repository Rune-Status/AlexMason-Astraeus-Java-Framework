package astraeus.net.codec.login;

/**
 * Represents the enumerated states of the login protocol
 * 
 * @author Vult-R
 */
public enum LoginDecoderState {

		/**
		 * The state that where the type of login connection is indicated.
		 * <p>
		 * New Connection : 16
		 * <p>
		 * Reconnecting : 18
		 * <p>
		 */
		CONNECTION_TYPE,

		/**
		 * The state where the size of the encrypted data is determined.
		 */
		PRECRYPTED,

		/**
		 * The state where the encrypted data is decoded.
		 */
		CRYPTED;
	
}
