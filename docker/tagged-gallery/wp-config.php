<?php
/**
 * The base configurations of the WordPress.
 *
 * This file has the following configurations: MySQL settings, Table Prefix,
 * Secret Keys, WordPress Language, and ABSPATH. You can find more information
 * by visiting {@link http://codex.wordpress.org/Editing_wp-config.php Editing
 * wp-config.php} Codex page. You can get the MySQL settings from your web host.
 *
 * This file is used by the wp-config.php creation script during the
 * installation. You don't have to use the web site, you can just copy this file
 * to "wp-config.php" and fill in the values.
 *
 * @package WordPress
 */

// ** MySQL settings - You can get this info from your web host ** //
/** The name of the database for WordPress */
define('DB_NAME', 'wp');

/** MySQL database username */
define('DB_USER', 'admin');

/** MySQL database password */
define('DB_PASSWORD', 'admin');

/** MySQL hostname */
define('DB_HOST', '192.168.59.103');

/** Database Charset to use in creating database tables. */
define('DB_CHARSET', 'utf8');

/** The Database Collate type. Don't change this if in doubt. */
define('DB_COLLATE', '');

/**#@+
 * Authentication Unique Keys and Salts.
 *
 * Change these to different unique phrases!
 * You can generate these using the {@link https://api.wordpress.org/secret-key/1.1/salt/ WordPress.org secret-key service}
 * You can change these at any point in time to invalidate all existing cookies. This will force all users to have to log in again.
 *
 * @since 2.6.0
 */
define('AUTH_KEY',         'j=FEqujElAj@R}~v:26^z]@_(?qy4m]|33Rbcs6qR[ATGo8*CP(0z5Ol,)!qm7`x');
define('SECURE_AUTH_KEY',  ';h^v0EzQ&D^[?!jRJ1OU5gB_Jpfuqhezef2R:jD<n(FRF+&|1U.tyc2>[o:fe>@/');
define('LOGGED_IN_KEY',    'x2%a[*(lJQ9v=-s-R0pJaY1%&C|J|a3)9bwrZtsr _044t^eX4ghr`AI;U5F*3sG');
define('NONCE_KEY',        '|A])J*/rSIttR+uMU|?!QiiNS},FjVq)/rhH:7.0:tX}_)#5-2{Z?M+Ttl(m=XY{');
define('AUTH_SALT',        'KI5YueQ+?RqB>,7+aOg]$O1p$$[W/W#n3)^-{Ek+Fjg_Vo&p)w5u?T5LrDGucjH}');
define('SECURE_AUTH_SALT', '^:ExpiLl}p;w}b!mi)>|.<o^1X:7MsMb<32<!if|iQ%e9Ne?1.F%2uN~kyxbZVc0');
define('LOGGED_IN_SALT',   '&7|Wf[-Tkz6[jRfj^Xth{{9#+LhLN6HE[<%QMIu+Bw1<-Ibv+4IsXsYn8Z8krQ[5');
define('NONCE_SALT',       '_Thg/#Cpbd^|SFtan?[K7;L>s]x+TKxU]|GDI(G/6Ne9m?=EFDe<q^|A3PZ1P(zX');

/**#@-*/

/**
 * WordPress Database Table prefix.
 *
 * You can have multiple installations in one database if you give each a unique
 * prefix. Only numbers, letters, and underscores please!
 */
$table_prefix  = 'wp_';

/**
 * WordPress Localized Language, defaults to English.
 *
 * Change this to localize WordPress. A corresponding MO file for the chosen
 * language must be installed to wp-content/languages. For example, install
 * de_DE.mo to wp-content/languages and set WPLANG to 'de_DE' to enable German
 * language support.
 */
define('WPLANG', '');

/**
 * For developers: WordPress debugging mode.
 *
 * Change this to true to enable the display of notices during development.
 * It is strongly recommended that plugin and theme developers use WP_DEBUG
 * in their development environments.
 */
define('WP_DEBUG', false);

/* That's all, stop editing! Happy blogging. */

/** Absolute path to the WordPress directory. */
if ( !defined('ABSPATH') )
	define('ABSPATH', dirname(__FILE__) . '/');

/** Sets up WordPress vars and included files. */
require_once(ABSPATH . 'wp-settings.php');
