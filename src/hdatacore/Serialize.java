/*
 *
 *
 *    Copyright 2009 The MITRE Corporation
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package hdatacore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import org.projecthdata.hdata.buildingblocks.allergy_types.Product;
import org.projecthdata.hdata.buildingblocks.core.Gender;
import org.projecthdata.hdata.hrf.HRF;
import org.projecthdata.hdata.hrf.HRFFactory;
import org.projecthdata.hdata.hrf.SectionPathExistsException;
import org.projecthdata.hdata.hrf.Section;
import org.projecthdata.hdata.hrf.SectionDocument;
import org.projecthdata.hdata.hrf.hDataXmlDocument;
import org.projecthdata.hdata.hrf.serialization.HRFFileSystemSerializer;
import org.projecthdata.hdata.hrf.util.DateConverter;
import org.projecthdata.hdata.hrf.util.hDataContentResolver;
import org.projecthdata.hdata.schemas._2009._06.allergy.Allergy;
import org.projecthdata.hdata.schemas._2009._06.allergy.Severity;
import org.projecthdata.hdata.schemas._2009._06.core.Address;
import org.projecthdata.hdata.schemas._2009._06.core.Name;
import org.projecthdata.hdata.schemas._2009._06.core.Telecom;
import org.projecthdata.hdata.schemas._2009._06.core.DateRange;
import org.projecthdata.hdata.schemas._2009._06.core.Language;
import org.projecthdata.hdata.schemas._2009._06.core.MaritalStatus;
import org.projecthdata.hdata.schemas._2009._06.core.Race;
import org.projecthdata.hdata.schemas._2009._06.medication.Medication;
import org.projecthdata.hdata.schemas._2009._06.medication.Dose;
import org.projecthdata.hdata.schemas._2009._06.medication.MedicationInformation;
import org.projecthdata.hdata.schemas._2009._06.patient_information.Patient;
import org.projecthdata.hdata.schemas._2009._11.metadata.DocumentMetaData;




/**
 *
 * @author GBEUCHELT
 */
public class Serialize {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {

            try {
                // TODO code application logic here
                HRFFactory fac = new HRFFactory();
                HRF hrf = fac.getHRFInstance();

                String propertiesFile = "org/projecthdata/hdata/buildingblocks/ccd.properties";

                InputStream in = ClassLoader.getSystemResourceAsStream(propertiesFile);
                if (in == null) {
                    throw new FileNotFoundException();
                }

                Properties props = new java.util.Properties();
                props.load(in);

                hDataContentResolver cr = new hDataContentResolver(props);

                Section patientinformation = new Section("Patient Information", cr.getNamespace(Patient.class), "patientinformation");

                Patient p = new Patient();
                Name n = new Name();

                n.setTitle("Dr");
                n.getGiven().add("Robert");
                n.setLastname("Bruce");
                n.setSuffix("Esquire");
                p.setName(n);

                Address ad = new Address();

                ad.getStreetAddress().add("1 Castle Rd");
                ad.setCity("Edinburgh");
                ad.setCountry("Scotland");
                ad.setStateOrProvince("Central Belt");
                ad.setZip("00000");

                p.getAddress().add(ad);

                Telecom tel = new Telecom();
                tel.setValue("44-131-1234");
                tel.setUse("yes");

                p.getTelecom().add(tel);

                Address bpad = new Address();
                bpad.getStreetAddress().add("1 Castle Rd");
                bpad.setCity("Edinburgh");
                bpad.setCountry("Scotland");
                bpad.setStateOrProvince("Central Belt");
                bpad.setZip("00000");
                p.setBirthPlace(bpad);

                try {
                    p.setBirthtime(DateConverter.getXLMDateFromUtilsDate(new Date(72, 3, 6)));
                } catch (DatatypeConfigurationException ex) {
                    Logger.getLogger(Serialize.class.getName()).log(Level.SEVERE, null, ex);
                }

                p.setId("00000000001");

                p.setGender(Gender.MALE.getGender());

                Language lang = new Language();
                lang.setCode("SCO");
                lang.setCodeSystem("ISO 639-2");
                lang.setDisplayName("Scots");

                p.getLanguage().add(lang);

                MaritalStatus ms = new MaritalStatus();
                ms.setCodeSystem("HL7");
                ms.setCode("M");
                ms.setDisplayName("Married");

                p.setMaritialStatus(ms);

                Race race = new Race();
                race.setCodeSystem("HL7");
                race.setCode("2L2116-2");
                race.setDisplayName("Scottish");

                p.getRace().add(race);

                //Actor actor = new Actor();
                //OrganizationClass org = new OrganizationClass();
                //org.setName("Country of Scotland");
                //Address orgad = new Address();
                //orgad.getStreetAddress().add("1 Castle Rd");
                //orgad.setCity("Edinburgh");
                //orgad.setCountry("Scotland");
                //orgad.setStateOrProvince("Central Belt");
                //orgad.setZip("00000");
                //org.getAddress().add(orgad);

                //PersonClass person = new PersonClass();
                //Name name = new Name();

                //name.setTitle("Dr");
                //name.getGiven().add("Robert");
                //name.setLastname("Bruce");
                //name.setSuffix("Esquire");
                //person.setName(n);

                //Address address = new Address();

                //address.getStreetAddress().add("1 Castle Rd");
                //address.setCity("Edinburgh");
                //address.setCountry("Scotland");
                //address.setStateOrProvince("Central Belt");
                //address.setZip("00000");

                //person.getAddress().add(address);

                //Telecom telecom = new Telecom();
                //telecom.setValue("44-131-1234");
                //telecom.setUse("yes");

                //person.getTelecom().add(telecom);

                //org.getPointOfContacts().getPointOfContact().add(person);
                //actor.setActor(org);

                //Description desc = new Description();
                //desc.setText("This is a description!");
                //CodedValue cv = new CodedValue();
                //cv.setCode("12");
                //cv.setCodeSystem("HL7");
                //cv.setDisplayName("Hello!");
                //desc.getCodedValue().add(cv);
                //p.setDescription(desc);

                //InformationSource is = new InformationSource();
                //try {
                //    is.setDate(DateConverter.getXLMDateFromUtilsDate(new Date(72, 3, 6)));
                //} catch (DatatypeConfigurationException ex) {
                //    Logger.getLogger(Serialize.class.getName()).log(Level.SEVERE, null, ex);
                //}
                //p.setInformationSource(is);

                hDataXmlDocument doc = new hDataXmlDocument(p);

                DocumentMetaData md = new DocumentMetaData();
                md.setDocumentId("12345");
                md.setMediaType("application/xml");

                DocumentMetaData.RecordDate rdd = new DocumentMetaData.RecordDate();
                rdd.setCreatedDateTime(DateConverter.getXLMDateFromUtilsDate(new Date()));

                SectionDocument sd = new SectionDocument(md, doc);

                patientinformation.addSectionDocument(sd);
                hrf.addSection(patientinformation, "/");

                Section adverse = new Section("Adverse Reactions", cr.getNamespace(Allergy.class), "adversereactions");
                hrf.addSection(adverse, "/");

                Section allergies = new Section("Allergies", cr.getNamespace(Allergy.class), "allergies");

                Allergy a = new Allergy();
                DateRange dr = new DateRange();
                dr.setHigh(DateConverter.getXLMDateFromUtilsDate(new Date(102, 5, 2)));
                dr.setLow(DateConverter.getXLMDateFromUtilsDate(new Date(77, 6, 2)));
                a.setAdverseEventDate(dr);



                a.setProduct(new Product("1.2.3.4.5.6.7", "Penicillin").getProduct());
                
                Severity s = new Severity();
                s.setCode("1");
                s.setCodeSystem("blah");
                s.setDisplayName("ouch");
                a.setSeverity(s);


                hDataXmlDocument hda = new hDataXmlDocument(a);
                DocumentMetaData mda = new DocumentMetaData();
                mda.setRecordDate(rdd);
                mda.setMediaType("application/xml");
                mda.setDocumentId("937321");

                SectionDocument sda = new SectionDocument(mda, hda);

                allergies.addSectionDocument(sda);

                hrf.addSection(allergies, "/adversereactions");

                Section meds = new Section("Medications", cr.getNamespace(Medication.class), "medications");

                Medication medi = new Medication();
                
                MedicationInformation mi = new MedicationInformation(); 
                MedicationInformation.ManufacturedMaterial mm = new MedicationInformation.ManufacturedMaterial(); 
                mm.setFreeTextBrandName("Advil");
                mm.setLotNumberText("AX-342-2009"); 
                mi.setManufacturedMaterial(mm);
                medi.setMedicationInformation(mi);

                Dose dose = new Dose();
                dose.setUnit("mg");
                dose.setValue("200");
                
                medi.setDose(dose);
                medi.setFreeTextSig("Take this for head aches.");

                DocumentMetaData medMd = new DocumentMetaData();
                medMd.setDocumentId("IBU-200-12312");
                medMd.setRecordDate(rdd);

                SectionDocument medSd = new SectionDocument(medMd, new hDataXmlDocument(medi));
                meds.addSectionDocument(medSd);
                hrf.addSection(meds, "/");

                try {
                    HRFFileSystemSerializer ser = new HRFFileSystemSerializer();

                    File file = new File("/tmp/hrf/patient");

                    deleteDirectory(file);

                    ser.serialize(file, hrf);
                } catch (IOException ex) {
                    Logger.getLogger(Serialize.class.getName()).log(Level.SEVERE, null, ex);
                }


            } catch (ClassNotFoundException ex) {
            Logger.getLogger(Serialize.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(Serialize.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SectionPathExistsException ex) {
            Logger.getLogger(Serialize.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
                Logger.getLogger(Serialize.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private static boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }
}
