package xyz.kebigon.yuzurss.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.junit.jupiter.api.Test;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

public class ItemTests
{
	private final SimpleDateFormat dateFormat = new SimpleDateFormat(Item.DATE_FORMAT_PATTERN);

	public ItemTests()
	{
		dateFormat.setTimeZone(TimeZone.getTimeZone(Item.DATE_FORMAT_TINEZONE));
	}

	@Test
	public void testBootstrapFeed() throws IllegalArgumentException, FeedException, IOException
	{
		final SyndFeedInput input = new SyndFeedInput();
		final SyndFeed feed = input.build(new XmlReader(getClass().getResource("/bootstrap.xml")));

		final Author author = new Author("{\"twitter\"=>\"getbootstrap\"}");

		final Item item0 = new Item(feed.getEntries().get(0), feed);
		assertEquals("https://blog.getbootstrap.com/2019/12/14/bootstrap-icons-alpha2", item0.getId());
		assertEquals("https://blog.getbootstrap.com/2019/12/14/bootstrap-icons-alpha2/", item0.getUrl());
		assertEquals("Bootstrap Icons Alpha 2", item0.getTitle());
		assertEquals(
				"<p>There’s a brand new update of Bootstrap Icons today with our second alpha release! We’ve updated nearly 20 icons and added over 100 new icons since our last release just a few weeks ago.</p>\n\n<h2 id=\"new-icons\">New icons</h2>\n\n<p>With over 120 new and updated icons, this is likely going to be our largest update before we our first stable release. We have some renamed icons, fixed bugs, new tools icons, new typography icons, tons of new arrows, and so much more.</p>\n\n<p><a href=\"https://icons.getbootstrap.com/\"><img src=\"/assets/img/2019/12/bootstrap-icons-alpha2-new.png\" alt=\"New icons in Alpha 2\" /></a></p>\n\n<h2 id=\"highlights\">Highlights</h2>\n\n<p>Here’s a summary of what’s been fixed, updated, or renamed in this release. For a full summary of what’s new, head to the <a href=\"https://github.com/twbs/icons/releases/tag/v1.0.0-alpha2\">GitHub release</a> or <a href=\"https://github.com/twbs/icons/pull/78\">Alpha 2 pull request</a>.</p>\n\n<ul>\n  <li><strong>Fixed:</strong>\n    <ul>\n      <li>Bootstrap icon stroke now 1px instead of 1.5px</li>\n      <li>tv-fill icon no longer has graphical glitch</li>\n      <li>circle-slash icon strokes now connect</li>\n      <li>trash icons now use a single shape</li>\n      <li>trash-fill icon no longer has a gap between lid and bin</li>\n      <li>layout-split no longer has space between vertical divide</li>\n    </ul>\n  </li>\n  <li><strong>Updated:</strong>\n    <ul>\n      <li>blockquote icons now feature more legible quotation marks</li>\n      <li>command icon now 1px smaller, no longer sitting on half pixel</li>\n      <li>gear icons now have rounded corners</li>\n      <li>eye is now outline by default (use new eye-fill variant if needed)</li>\n      <li>redrew sound waves on volume icons</li>\n      <li>corrected (reversed) the direction of backspace icon</li>\n    </ul>\n  </li>\n  <li><strong>Renamed:</strong>\n    <ul>\n      <li>changed microphone to mic</li>\n      <li>existing expand/contract icons are now angle-expand and angle-contract</li>\n    </ul>\n  </li>\n</ul>\n\n<h2 id=\"get-em\">Get ‘em</h2>\n\n<p>Alpha 2 has been published to GitHub and npm (package name <code class=\"language-plaintext highlighter-rouge\">bootstrap-icons</code>). Get your hands on it <a href=\"https://github.com/twbs/icons/releases\">from GitHub</a>, by updating to <code class=\"language-plaintext highlighter-rouge\">v1.0.0-alpha2</code>, or by snagging the <a href=\"https://www.figma.com/file/0xfDVFogWu6g15bVOvBzxS/Bootstrap-Icons-v1.0.0-alpha2\">icons from Figma</a>.</p>\n\n<p>&lt;3,<br /></p>\n\n<p><a href=\"https://github.com/mdo\">@mdo</a> &amp; <a href=\"https://github.com/twbs\">team</a></p>",
				item0.getContentHtml());
		assertEquals(
				"There’s a brand new update of Bootstrap Icons today with our second alpha release! We’ve updated nearly 20 icons and added over 100 new icons since our last release just a few weeks ago.",
				item0.getSummary());
		assertEquals("2019-12-14T00:00:00.000Z", dateFormat.format(item0.getDatePublished()));
		assertEquals(author, item0.getAuthor());

		final Item item1 = new Item(feed.getEntries().get(1), feed);
		assertEquals("https://blog.getbootstrap.com/2019/11/28/bootstrap-4-4-1", item1.getId());
		assertEquals("https://blog.getbootstrap.com/2019/11/28/bootstrap-4-4-1/", item1.getUrl());
		assertEquals("Bootstrap 4.4.1", item1.getTitle());
		assertEquals(
				"<p>Today we’re shipping <a href=\"https://github.com/twbs/bootstrap/releases/tag/v4.4.1\">Bootstrap v4.4.1</a>!</p>\n\n<p>In <a href=\"/2019/11/26/bootstrap-4-4-0/\">v4.4.0</a>, we added <code class=\"language-plaintext highlighter-rouge\">add()</code> and <code class=\"language-plaintext highlighter-rouge\">subtract()</code> functions to avoid errors when using zero values in CSS’s built in <code class=\"language-plaintext highlighter-rouge\">calc()</code> function. While these functions work as expected with our build system, which is based on <code class=\"language-plaintext highlighter-rouge\">node-sass</code>, some alert developers noticed that <a href=\"https://github.com/twbs/bootstrap/issues/29743\">things broke when using another Sass compiler</a> like Dart Sass or Ruby Sass. To resolve this issue, we’ve tweaked these functions a bit to output what we would expect.</p>\n\n<p>Lastly, we also added a <a href=\"https://github.com/twbs/bootstrap/pull/29762\">theming fix</a> for some custom forms in a disabled fieldset.</p>\n\n<p>&lt;3,<br /></p>\n\n<p><a href=\"https://github.com/mdo\">@mdo</a> &amp; <a href=\"https://github.com/twbs\">team</a></p>",
				item1.getContentHtml());
		assertEquals("Today we’re shipping Bootstrap v4.4.1!", item1.getSummary());
		assertEquals("2019-11-28T00:00:00.000Z", dateFormat.format(item1.getDatePublished()));
		assertEquals(author, item1.getAuthor());

		final Item item2 = new Item(feed.getEntries().get(2), feed);
		assertEquals("https://blog.getbootstrap.com/2019/11/26/bootstrap-4-4-0", item2.getId());
		assertEquals("https://blog.getbootstrap.com/2019/11/26/bootstrap-4-4-0/", item2.getUrl());
		assertEquals("Bootstrap 4.4.0", item2.getTitle());
		assertEquals(
				"<p>Bootstrap 4 has a new update with a handful of feature changes. We’ve had quite the lengthy pull request to add responsive containers—big thanks to the developers who contribute to Bootstrap for sticking with it and helping us along the way. Nearly all new features will be carried forward into Bootstrap 5, so feel free to start using them now.</p>\n\n<h2 id=\"highlights\">Highlights</h2>\n\n<p>Here’s what you need to know about v4.4.0. Remember that with every minor and major release of Bootstrap, we ship a new URL for our hosted docs to ensure URLs continue to work.</p>\n\n<ul>\n  <li><strong>New responsive containers!</strong> Over a year in the making, fluid up to a particular breakpoint, available for all responsive tiers.</li>\n  <li><strong>New responsive <code class=\"language-plaintext highlighter-rouge\">.row-cols</code> classes</strong> for quickly specifying the number of columns across breakpoints. This one is huge for those of you who have asked for responsive card decks.</li>\n  <li><strong>New <code class=\"language-plaintext highlighter-rouge\">escape-svg()</code> function</strong> for simplifying our embedded <code class=\"language-plaintext highlighter-rouge\">background-image</code> SVGs for forms and more.</li>\n  <li><strong>New <code class=\"language-plaintext highlighter-rouge\">add()</code> and <code class=\"language-plaintext highlighter-rouge\">subtract()</code> functions</strong> for avoiding errors and zero values from CSS’s built in <code class=\"language-plaintext highlighter-rouge\">calc</code> feature.</li>\n  <li><strong>New <code class=\"language-plaintext highlighter-rouge\">make-col-auto()</code> mixin</strong> to make our <code class=\"language-plaintext highlighter-rouge\">.col-auto</code> class available with custom HTML.</li>\n  <li>Fixed an issue with Microsoft Edge not picking up <code class=\"language-plaintext highlighter-rouge\">:disabled</code> styles by moving selectors to <code class=\"language-plaintext highlighter-rouge\">[disabled]</code>.</li>\n  <li><strong>Deprecated:</strong> <code class=\"language-plaintext highlighter-rouge\">bg-variant()</code>, <code class=\"language-plaintext highlighter-rouge\">nav-divider()</code>, and <code class=\"language-plaintext highlighter-rouge\">form-control-focus()</code> mixins are now deprecated as they’re going away in v5.</li>\n  <li>Updated our spacing and alignment for modal footer elements like buttons to automatically wrap when space is constrained.</li>\n  <li>More flexible form control validation styles thanks to fewer chained selectors. Also updated the <code class=\"language-plaintext highlighter-rouge\">:invalid</code> validation icon to be an alert instead of an <code class=\"language-plaintext highlighter-rouge\">&amp;times;</code> to avoid confusion with browser functionality for clearing the form field value.</li>\n  <li>Fixed a couple dozen CSS and JS bugs.</li>\n  <li>Moved to GitHub Actions for CI/CD! Expect more updates to our CI setup over time here while Actions evolves.</li>\n  <li>Updated documentation to fix links and typos, improved landmarks for secondary navigation, and a new security doc for guidelines on reporting potential vulnerabilities.</li>\n</ul>\n\n<p>We’ve shipped a lot more in this release, so be sure to check out the <a href=\"https://github.com/twbs/bootstrap/issues?q=project%3Atwbs%2Fbootstrap%2F18+is%3Aclosed+sort%3Aupdated-desc\">v4.4.0 ship list of closed issues and merged pull requests</a> for more details.</p>\n\n<p><a href=\"https://getbootstrap.com/docs/4.4/\">Head to to the v4.4.0 docs</a> to see the latest in action. The full release has been published to npm and will soon appear on the BootstrapCDN and Rubygems.</p>\n\n<h2 id=\"support-the-team\">Support the team</h2>\n\n<p>Visit our <a href=\"https://opencollective.com/bootstrap\">Open Collective page</a> or our <a href=\"https://github.com/orgs/twbs/people\">team members</a>’ GitHub profiles to help support the maintainers contributing to Bootstrap.</p>\n\n<p>&lt;3,<br /></p>\n\n<p><a href=\"https://github.com/mdo\">@mdo</a> &amp; <a href=\"https://github.com/twbs\">team</a></p>",
				item2.getContentHtml());
		assertEquals(
				"Bootstrap 4 has a new update with a handful of feature changes. We’ve had quite the lengthy pull request to add responsive containers—big thanks to the developers who contribute to Bootstrap for sticking with it and helping us along the way. Nearly all new features will be carried forward into Bootstrap 5, so feel free to start using them now.",
				item2.getSummary());
		assertEquals("2019-11-26T00:00:00.000Z", dateFormat.format(item2.getDatePublished()));
		assertEquals(author, item2.getAuthor());
	}

	@Test
	public void testGithubFeed() throws IllegalArgumentException, FeedException, IOException
	{
		final SyndFeedInput input = new SyndFeedInput();
		final SyndFeed feed = input.build(new XmlReader(getClass().getResource("/github.xml")));

		final Author author = new Author("alexbakker");

		final Item item0 = new Item(feed.getEntries().get(0), feed);
		assertEquals("tag:github.com,2008:Repository/65757761/v1.1.4", item0.getId());
		assertEquals("https://github.com/beemdevelopment/Aegis/releases/tag/v1.1.4", item0.getUrl());
		assertEquals("v1.1.4", item0.getTitle());
		assertEquals(
				"<p>This is a minor release.</p>\n<h2>Fixed bugs</h2>\n<ul>\n<li>The export filename was missing the \".json\" extension in some cases</li>\n</ul>\n<p>The APK released to the Play Store has <code>versionCode</code> set to 29 instead of 28, because the update had to be resubmitted due to an erroneous rejection by Google.</p>",
				item0.getContentHtml());
		assertEquals(null, item0.getSummary());
		assertEquals("2020-01-23T09:47:25.000Z", dateFormat.format(item0.getDatePublished()));
		assertEquals(author, item0.getAuthor());

		final Item item1 = new Item(feed.getEntries().get(1), feed);
		assertEquals("tag:github.com,2008:Repository/65757761/v1.1.3", item1.getId());
		assertEquals("https://github.com/beemdevelopment/Aegis/releases/tag/v1.1.3", item1.getUrl());
		assertEquals("v1.1.3", item1.getTitle());
		assertEquals(
				"<p>This is a minor release.</p>\n<h2>New features</h2>\n<ul>\n<li>Password reminder for users who use biometric unlock</li>\n</ul>\n<h2>Fixed bugs</h2>\n<ul>\n<li>Tokens would not refresh in some rare cases</li>\n</ul>\n<p>Expect an automatic backup feature to be released soon!</p>",
				item1.getContentHtml());
		assertEquals(null, item1.getSummary());
		assertEquals("2020-01-20T20:42:14.000Z", dateFormat.format(item1.getDatePublished()));
		assertEquals(author, item1.getAuthor());

		final Item item2 = new Item(feed.getEntries().get(2), feed);
		assertEquals("tag:github.com,2008:Repository/65757761/v1.1.2", item2.getId());
		assertEquals("https://github.com/beemdevelopment/Aegis/releases/tag/v1.1.2", item2.getUrl());
		assertEquals("v1.1.2", item2.getTitle());
		assertEquals(
				"<h2>New features</h2>\n<ul>\n<li>Ability to select multiple entries</li>\n<li>Ability to select a file location when exporting the vault (including cloud providers like Google Drive)</li>\n<li>Explanation and warning for the security options</li>\n<li>Removed external storage permissions</li>\n</ul>",
				item2.getContentHtml());
		assertEquals(null, item2.getSummary());
		assertEquals("2020-01-18T13:49:25.000Z", dateFormat.format(item2.getDatePublished()));
		assertEquals(author, item2.getAuthor());
	}

	@Test
	public void testSoundCloudFeed() throws IllegalArgumentException, FeedException, IOException
	{
		final SyndFeedInput input = new SyndFeedInput();
		final SyndFeed feed = input.build(new XmlReader(getClass().getResource("/soundcloud.xml")));

		final Author author = new Author("The Privacy, Security, & OSINT Show");

		final Item item0 = new Item(feed.getEntries().get(0), feed);
		assertEquals("tag:soundcloud,2010:tracks/806159224", item0.getId());
		assertEquals("https://soundcloud.com/user-98066669/167-this-week-in-privacy-osint", item0.getUrl());
		assertEquals("167-This Week In Privacy & OSINT", item0.getTitle());
		assertEquals(
				"EPISODE 167-This Week In Privacy & OSINTThis week I discuss a full range of privacy & OSINT topics including COVID-19 tracking, the latest iPhoneSE, more Zoom issues, MySudo news, another reason to hide your home address, Pastebin complaints, and a new free email research API.Support for this show comes directly from my new books Extreme Privacy and Open Source Intelligence Techniques (7th Edition). More details can be found at https://inteltechniques.com/books.html.Listen to ALL episodes at https://inteltechniques.com/podcast.htmlSHOW NOTES:THIS WEEK IN PRIVACY:COVID-19 TracingiPhone SE 2Zoom Passwords for SaleZoom Cleaning: https://github.com/kris-anderson/remove-zoom-macosMySudo Android: https://twitter.com/MySudoApp/status/1253373325822345218MySudo Virtual Cards: https://support.mysudo.com/hc/en-us/articles/360043197173-What-are-MySudo-virtual-cards-and-how-do-they-work-Officer Doxing: https://www.huffpost.com/entry/ammon-bundy-protest-idaho-anti-vaxxer_n_5ea0b7d5c5b69150246cfbfcOSINT:https://www.neutrinoapi.com/account/tools/https://portswigger.net/daily-swig/pastebin-hints-at-new-research-subscription-model-after-axing-scraping-apiData Removal Workbook:https://inteltechniques.com/data/workbook.pdfAffiliate Links:VPN Considerations: https://inteltechniques.com/vpn.htmlProtonVPN: https://proton.go2cloud.org/aff_c?offer_id=6&aff_id=1519ProtonMail: http://proton.go2cloud.org/aff_c?offer_id=15&aff_id=1519PIA: https://www.privateinternetaccess.com/pages/buy-vpn/crimeinfoAmazon: https://amzn.to/339avqo",
				item0.getContentHtml());
		assertEquals(
				"EPISODE 167-This Week In Privacy & OSINTThis week I discuss a full range of privacy & OSINT topics including COVID-19 tracking, the latest iPhoneSE, more Zoom issues, MySudo news, another reason to hide your home address, Pastebin complaints, and a new free email research API.Support for this show comes directly from my new books Extreme Privacy and Open Source Intelligence Techniques (7th Edition). More details can be found at https://inteltechniques.com/books.html.Listen to ALL episodes at https://inteltechniques.com/podcast.htmlSHOW NOTES:THIS WEEK IN PRIVACY:COVID-19 TracingiPhone SE 2Zoom Passwords for SaleZoom Cleaning: https://github.com/kris-anderson/remove-zoom-macosMySudo Android: https://twitter.com/MySudoApp/status/1253373325822345218MySudo Virtual Cards: https://support.mysudo.com/hc/en-us/articles/360043197173-What-are-MySudo-virtual-cards-and-how-do-they-work-Officer Doxing: https://www.huffpost.com/entry/ammon-bundy-protest-idaho-anti-vaxxer_n_5ea0b7d5c5b69150246cfbfcOSINT:https://www.neutrinoapi.com/account/tools/https://portswigger.net/daily-swig/pastebin-hints-at-new-research-subscription-model-after-axing-scraping-apiData Removal Workbook:https://inteltechniques.com/data/workbook.pdfAffiliate Links:VPN Considerations: https://inteltechniques.com/vpn.htmlProtonVPN: https://proton.go2cloud.org/aff_c?offer_id=6&aff_id=1519ProtonMail: http://proton.go2cloud.org/aff_c?offer_id=15&aff_id=1519PIA: https://www.privateinternetaccess.com/pages/buy-vpn/crimeinfoAmazon: https://amzn.to/339avqo",
				item0.getSummary());
		assertEquals("2020-04-24T15:09:45.000Z", dateFormat.format(item0.getDatePublished()));
		assertEquals(author, item0.getAuthor());

		final Item item1 = new Item(feed.getEntries().get(1), feed);
		assertEquals("tag:soundcloud,2010:tracks/800868520", item1.getId());
		assertEquals("https://soundcloud.com/user-98066669/166-home-firewalls-revisited", item1.getUrl());
		assertEquals("166-Home Firewalls Revisited", item1.getTitle());
		assertEquals(
				"EPISODE 166-Home Firewalls RevisitedThis week I revisit the importance of a VPN-protected home firewall. It is the one device which is mandatory for all of my clients needing a full privacy reboot.Support for this show comes directly from my new books Extreme Privacy and Open Source Intelligence Techniques (7th Edition). More details can be found at https://inteltechniques.com/books.html.Listen to ALL episodes at https://inteltechniques.com/podcast.htmlSHOW NOTES:HOME FIREWALLS REVISITED:https://inteltechniques.com/firewall/Data Removal Workbook:https://inteltechniques.com/data/workbook.pdfAffiliate Links:VPN Considerations: https://inteltechniques.com/vpn.htmlProtonVPN: https://proton.go2cloud.org/aff_c?offer_id=6&aff_id=1519ProtonMail: http://proton.go2cloud.org/aff_c?offer_id=15&aff_id=1519PIA: https://www.privateinternetaccess.com/pages/buy-vpn/crimeinfoAmazon: https://amzn.to/339avqoSilent Pocket: https://silent-pocket.com/discount/IntelTechniques",
				item1.getContentHtml());
		assertEquals(
				"EPISODE 166-Home Firewalls RevisitedThis week I revisit the importance of a VPN-protected home firewall. It is the one device which is mandatory for all of my clients needing a full privacy reboot.Support for this show comes directly from my new books Extreme Privacy and Open Source Intelligence Techniques (7th Edition). More details can be found at https://inteltechniques.com/books.html.Listen to ALL episodes at https://inteltechniques.com/podcast.htmlSHOW NOTES:HOME FIREWALLS REVISITED:https://inteltechniques.com/firewall/Data Removal Workbook:https://inteltechniques.com/data/workbook.pdfAffiliate Links:VPN Considerations: https://inteltechniques.com/vpn.htmlProtonVPN: https://proton.go2cloud.org/aff_c?offer_id=6&aff_id=1519ProtonMail: http://proton.go2cloud.org/aff_c?offer_id=15&aff_id=1519PIA: https://www.privateinternetaccess.com/pages/buy-vpn/crimeinfoAmazon: https://amzn.to/339avqoSilent Pocket: https://silent-pocket.com/discount/IntelTechniques",
				item1.getSummary());
		assertEquals("2020-04-17T15:05:40.000Z", dateFormat.format(item1.getDatePublished()));
		assertEquals(author, item1.getAuthor());

		final Item item2 = new Item(feed.getEntries().get(2), feed);
		assertEquals("tag:soundcloud,2010:tracks/795379366", item2.getId());
		assertEquals("https://soundcloud.com/user-98066669/165-what-amazon-twitter-knows-about-you-and-me", item2.getUrl());
		assertEquals("165-What Amazon & Twitter Knows About You (and Me)", item2.getTitle());
		assertEquals(
				"This week I analyze the data I received from Twitter and Amazon in reference to my accounts. I found a few surprises...Support for this show comes directly from my new books Extreme Privacy and Open Source Intelligence Techniques (7th Edition). More details can be found at https://inteltechniques.com/books.html.Listen to ALL episodes at https://inteltechniques.com/podcast.htmlSHOW NOTES:WHAT AMAZON & TWITTER KNOWS ABOUT YOU (AND ME):https://help.twitter.com/en/safety-and-security/data-through-partnershipshttps://www.amazon.com/gp/help/customer/display.html?nodeId=G5NBVNN2RHXD5BUWhttps://www.amazon.com/gp/help/customer/display.html?nodeId=GDK92DNLSGWTV6MPData Removal Workbook:https://inteltechniques.com/data/workbook.pdfAffiliate Links:VPN Considerations: https://inteltechniques.com/vpn.htmlProtonVPN: https://proton.go2cloud.org/aff_c?offer_id=6&aff_id=1519ProtonMail: http://proton.go2cloud.org/aff_c?offer_id=15&aff_id=1519PIA: https://www.privateinternetaccess.com/pages/buy-vpn/crimeinfoAmazon: https://amzn.to/339avqoSilent Pocket: https://silent-pocket.com/discount/IntelTechniques",
				item2.getContentHtml());
		assertEquals(
				"This week I analyze the data I received from Twitter and Amazon in reference to my accounts. I found a few surprises...Support for this show comes directly from my new books Extreme Privacy and Open Source Intelligence Techniques (7th Edition). More details can be found at https://inteltechniques.com/books.html.Listen to ALL episodes at https://inteltechniques.com/podcast.htmlSHOW NOTES:WHAT AMAZON & TWITTER KNOWS ABOUT YOU (AND ME):https://help.twitter.com/en/safety-and-security/data-through-partnershipshttps://www.amazon.com/gp/help/customer/display.html?nodeId=G5NBVNN2RHXD5BUWhttps://www.amazon.com/gp/help/customer/display.html?nodeId=GDK92DNLSGWTV6MPData Removal Workbook:https://inteltechniques.com/data/workbook.pdfAffiliate Links:VPN Considerations: https://inteltechniques.com/vpn.htmlProtonVPN: https://proton.go2cloud.org/aff_c?offer_id=6&aff_id=1519ProtonMail: http://proton.go2cloud.org/aff_c?offer_id=15&aff_id=1519PIA: https://www.privateinternetaccess.com/pages/buy-vpn/crimeinfoAmazon: https://amzn.to/339avqoSilent Pocket: https://silent-pocket.com/discount/IntelTechniques",
				item2.getSummary());
		assertEquals("2020-04-10T15:11:00.000Z", dateFormat.format(item2.getDatePublished()));
		assertEquals(author, item2.getAuthor());
	}

	@Test
	public void testSuumoFeed() throws IllegalArgumentException, FeedException, IOException
	{
		final SyndFeedInput input = new SyndFeedInput();
		final SyndFeed feed = input.build(new XmlReader(getClass().getResource("/suumo.xml")));

		final Author author = new Author("SUUMO(スーモ)");

		final Item item0 = new Item(feed.getEntries().get(0), feed);
		assertEquals("https://suumo.jp/jj/bukken/shosai/JJ010FJ100/?ar=030&bs=021&nc=93385930&ta=12&sc=12234", item0.getId());
		assertEquals("https://suumo.jp/jj/bukken/shosai/JJ010FJ100/?ar=030&bs=021&nc=93385930&ta=12&sc=12234", item0.getUrl());
		assertEquals("物件名：自然の中に佇む、南房総の家", item0.getTitle());
		assertEquals("千葉県南房総市山田990万円ＪＲ内房線岩井623m&sup2;（登記）60.45m&sup2;（登記）1LDK1998年10月", item0.getContentHtml());
		assertEquals("千葉県南房総市山田990万円ＪＲ内房線岩井623m&sup2;（登記）60.45m&sup2;（登記）1LDK1998年10月", item0.getSummary());
		assertEquals(null, item0.getDatePublished());
		assertEquals(author, item0.getAuthor());

		final Item item1 = new Item(feed.getEntries().get(1), feed);
		assertEquals("https://suumo.jp/jj/bukken/shosai/JJ010FJ100/?ar=030&bs=021&nc=93373816&ta=11&sc=11242", item1.getId());
		assertEquals("https://suumo.jp/jj/bukken/shosai/JJ010FJ100/?ar=030&bs=021&nc=93373816&ta=11&sc=11242", item1.getUrl());
		assertEquals("物件名：大字高萩（武蔵高萩駅） 1180万円", item1.getTitle());
		assertEquals("埼玉県日高市大字高萩1180万円ＪＲ川越線武蔵高萩徒歩9分110.18m&sup2;（登記）87.58m&sup2;（登記）4LDK1980年2月", item1.getContentHtml());
		assertEquals("埼玉県日高市大字高萩1180万円ＪＲ川越線武蔵高萩徒歩9分110.18m&sup2;（登記）87.58m&sup2;（登記）4LDK1980年2月", item1.getSummary());
		assertEquals(null, item1.getDatePublished());
		assertEquals(author, item1.getAuthor());

		final Item item2 = new Item(feed.getEntries().get(2), feed);
		assertEquals("https://suumo.jp/jj/bukken/shosai/JJ010FJ100/?ar=030&bs=021&nc=93403901&ta=10&sc=10201", item2.getId());
		assertEquals("https://suumo.jp/jj/bukken/shosai/JJ010FJ100/?ar=030&bs=021&nc=93403901&ta=10&sc=10201", item2.getUrl());
		assertEquals("物件名：前橋市上細井町　中古戸建", item2.getTitle());
		assertEquals("群馬県前橋市上細井町1750万円上毛電鉄三俣徒歩38分202.5m&sup2;110.13m&sup2;4LDK2010年9月", item2.getContentHtml());
		assertEquals("群馬県前橋市上細井町1750万円上毛電鉄三俣徒歩38分202.5m&sup2;110.13m&sup2;4LDK2010年9月", item2.getSummary());
		assertEquals(null, item2.getDatePublished());
		assertEquals(author, item2.getAuthor());
	}

	@Test
	public void testYoutubeFeed() throws IllegalArgumentException, FeedException, IOException
	{
		final SyndFeedInput input = new SyndFeedInput();
		final SyndFeed feed = input.build(new XmlReader(getClass().getResource("/youtube.xml")));

		final Author author = new Author("e-penser");

		final Item item0 = new Item(feed.getEntries().get(0), feed);
		assertEquals("yt:video:hJe5MDMWOaU", item0.getId());
		assertEquals("https://www.youtube.com/watch?v=hJe5MDMWOaU", item0.getUrl());
		assertEquals("Les trous noirs (1/2) - 48 - e-penser", item0.getTitle());
		assertEquals(null, item0.getContentHtml());
		assertEquals(null, item0.getSummary());
		assertEquals("2020-01-23T08:09:43.000Z", dateFormat.format(item0.getDatePublished()));
		assertEquals(author, item0.getAuthor());

		final Item item1 = new Item(feed.getEntries().get(1), feed);
		assertEquals("yt:video:Sk7Ia2Lsuak", item1.getId());
		assertEquals("https://www.youtube.com/watch?v=Sk7Ia2Lsuak", item1.getUrl());
		assertEquals("La migraine est une horreur - 47 - e-penser", item1.getTitle());
		assertEquals(null, item1.getContentHtml());
		assertEquals(null, item1.getSummary());
		assertEquals("2020-01-09T15:51:58.000Z", dateFormat.format(item1.getDatePublished()));
		assertEquals(author, item1.getAuthor());

		final Item item2 = new Item(feed.getEntries().get(2), feed);
		assertEquals("yt:video:1Bn50keR6UY", item2.getId());
		assertEquals("https://www.youtube.com/watch?v=1Bn50keR6UY", item2.getUrl());
		assertEquals("Le mathématicien nul de l'Indiana - Flash 09 - e-penser", item2.getTitle());
		assertEquals(null, item2.getContentHtml());
		assertEquals(null, item2.getSummary());
		assertEquals("2019-12-27T17:00:25.000Z", dateFormat.format(item2.getDatePublished()));
		assertEquals(author, item2.getAuthor());
	}
}
