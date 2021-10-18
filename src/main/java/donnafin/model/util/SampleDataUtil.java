package donnafin.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import donnafin.model.AddressBook;
import donnafin.model.ReadOnlyAddressBook;
import donnafin.model.person.Address;
import donnafin.model.person.Asset;
import donnafin.model.person.Email;
import donnafin.model.person.Liability;
import donnafin.model.person.Name;
import donnafin.model.person.Notes;
import donnafin.model.person.Person;
import donnafin.model.person.Phone;
import donnafin.model.person.Policy;
import donnafin.model.tag.Tag;


/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    private static final String ATTRIBUTE_DELIMITER = ";;;";

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("friends"), new Notes("Likes bread"),
                    getPolicies("Policy name;;;Policy insurer;;;policy total value insured;;;policy yearly "
                            + "premiums;;;policy commission"),
                    getLiabilities("liability name;;;liability type;;;liability value;;;liability remarks"),
                    getAssets("asset name;;;asset type;;;asset value;;;asset remarks")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("colleagues", "friends"), new Notes("Has 2 children"),
                    getPolicies("Policy name;;;Policy insurer;;;policy total value insured;;;policy yearly "
                            + "premiums;;;policy commission"),
                    getLiabilities("liability name;;;liability type;;;liability value;;;liability remarks"),
                    getAssets("asset name;;;asset type;;;asset value;;;asset remarks")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("neighbours"), new Notes(""),
                    getPolicies("Policy name;;;Policy insurer;;;policy total value insured;;;policy yearly "
                            + "premiums;;;policy commission"),
                    getLiabilities("liability name;;;liability type;;;liability value;;;liability remarks"),
                    getAssets("asset name;;;asset type;;;asset value;;;asset remarks")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("family"), new Notes(""),
                    getPolicies("Policy name;;;Policy insurer;;;policy total value insured;;;policy yearly "
                            + "premiums;;;policy commission"),
                    getLiabilities("liability name;;;liability type;;;liability value;;;liability remarks"),
                    getAssets("asset name;;;asset type;;;asset value;;;asset remarks")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet("classmates"), new Notes(""),
                    getPolicies("Policy name;;;Policy insurer;;;policy total value insured;;;policy yearly "
                            + "premiums;;;policy commission"),
                    getLiabilities("liability name;;;liability type;;;liability value;;;liability remarks"),
                    getAssets("asset name;;;asset type;;;asset value;;;asset remarks")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet("colleagues"), new Notes(""),
                    getPolicies("Policy name;;;Policy insurer;;;policy total value insured;;;policy yearly "
                            + "premiums;;;policy commission"),
                    getLiabilities("liability name;;;liability type;;;liability value;;;liability remarks"),
                    getAssets("asset name;;;asset type;;;asset value;;;asset remarks")),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a policy set containing the list of strings given.
     */
    public static Set<Policy> getPolicies(String... strings) {
        return Arrays.stream(strings)
                .map(x -> new Policy(x.split(ATTRIBUTE_DELIMITER)))
                .collect(Collectors.toSet());
    }

    /**
     * Returns an asset set containing the list of strings given.
     */
    public static Set<Asset> getAssets(String... strings) {
        return Arrays.stream(strings)
                .map(x -> new Asset(x.split(ATTRIBUTE_DELIMITER)))
                .collect(Collectors.toSet());
    }

    /**
     * Returns a liability set containing the list of strings given.
     */
    public static Set<Liability> getLiabilities(String... strings) {
        return Arrays.stream(strings)
                .map(x -> new Liability(x.split(ATTRIBUTE_DELIMITER)))
                .collect(Collectors.toSet());
    }
}
