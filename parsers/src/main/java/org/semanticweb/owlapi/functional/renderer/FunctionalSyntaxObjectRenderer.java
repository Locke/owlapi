/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.functional.renderer;

import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.ANNOTATION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.ANNOTATION_ASSERTION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.ANNOTATION_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.ANNOTATION_PROPERTY_DOMAIN;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.ANNOTATION_PROPERTY_RANGE;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.ASYMMETRIC_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.BODY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.BUILT_IN_ATOM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.CLASS;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.CLASS_ASSERTION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.CLASS_ATOM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATATYPE;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATATYPE_DEFINITION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATATYPE_RESTRICTION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_ALL_VALUES_FROM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_COMPLEMENT_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_EXACT_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_HAS_VALUE;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_INTERSECTION_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_MAX_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_MIN_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_ONE_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_PROPERTY_ATOM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_PROPERTY_DOMAIN;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_PROPERTY_RANGE;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_RANGE_ATOM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_SOME_VALUES_FROM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DATA_UNION_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DECLARATION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DIFFERENT_INDIVIDUALS;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DIFFERENT_INDIVIDUALS_ATOM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DISJOINT_CLASSES;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DISJOINT_DATA_PROPERTIES;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DISJOINT_OBJECT_PROPERTIES;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DISJOINT_UNION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.DL_SAFE_RULE;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.EQUIVALENT_CLASSES;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.EQUIVALENT_DATA_PROPERTIES;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.EQUIVALENT_OBJECT_PROPERTIES;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.FUNCTIONAL_DATA_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.FUNCTIONAL_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.HAS_KEY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.HEAD;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.IMPORT;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.INVERSE_FUNCTIONAL_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.INVERSE_OBJECT_PROPERTIES;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.IRREFLEXIVE_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.NAMED_INDIVIDUAL;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.NEGATIVE_DATA_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.NEGATIVE_OBJECT_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_ALL_VALUES_FROM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_COMPLEMENT_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_EXACT_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_HAS_SELF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_HAS_VALUE;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_INTERSECTION_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_INVERSE_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_MAX_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_MIN_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_ONE_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_PROPERTY_ATOM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_PROPERTY_CHAIN;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_PROPERTY_DOMAIN;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_PROPERTY_RANGE;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_SOME_VALUES_FROM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.OBJECT_UNION_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.ONTOLOGY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.REFLEXIVE_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.SAME_INDIVIDUAL;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.SAME_INDIVIDUAL_ATOM;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.SUB_ANNOTATION_PROPERTY_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.SUB_CLASS_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.SUB_DATA_PROPERTY_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.SUB_OBJECT_PROPERTY_OF;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.SYMMETRIC_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.TRANSITIVE_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.VARIABLE;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.formats.PrefixDocumentFormat;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLCardinalityRestriction;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLPropertyRange;
import org.semanticweb.owlapi.model.OWLQuantifiedDataRestriction;
import org.semanticweb.owlapi.model.OWLQuantifiedObjectRestriction;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.util.AnnotationValueShortFormProvider;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.EscapeUtils;
import org.semanticweb.owlapi.util.ShortFormFromRDFSLabelAxiomListProvider;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;

/**
 * The Class OWLObjectRenderer.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class FunctionalSyntaxObjectRenderer implements OWLObjectVisitor {

    protected final Optional<OWLOntology> ont;
    private final Writer writer;
    private DefaultPrefixManager defaultPrefixManager;
    protected Optional<ShortFormProvider> labelMaker = Optional.empty();
    private Optional<PrefixManager> prefixManager = Optional.empty();
    private boolean writeEntitiesAsURIs = true;
    private boolean addMissingDeclarations = true;

    protected Stream<? extends OWLAxiom> retrieve(OWLEntity e, OWLOntology o) {
        if (e.isOWLClass()) {
            return o.axioms((OWLClass) e, EXCLUDED);
        }
        if (e.isOWLObjectProperty()) {
            return o.axioms((OWLObjectProperty) e, EXCLUDED);
        }
        if (e.isOWLDataProperty()) {
            return o.axioms((OWLDataProperty) e, EXCLUDED);
        }
        if (e.isOWLNamedIndividual()) {
            return o.axioms((OWLNamedIndividual) e, EXCLUDED);
        }
        if (e.isOWLDatatype()) {
            return o.axioms((OWLDatatype) e, EXCLUDED);
        }
        if (e.isOWLAnnotationProperty()) {
            return o.axioms((OWLAnnotationProperty) e, EXCLUDED);
        }
        return Stream.empty();
    }



    /**
     * @param ontology the ontology
     * @param writer the writer
     */
    public FunctionalSyntaxObjectRenderer(@Nullable OWLOntology ontology, Writer writer) {
        ont = Optional.ofNullable(ontology);
        this.writer = writer;
        defaultPrefixManager = new DefaultPrefixManager();
        ont.ifPresent(o -> {
            OWLDocumentFormat ontologyFormat = o.getNonnullFormat();
            // reuse the setting on the existing format
            addMissingDeclarations = ontologyFormat.isAddMissingTypes();
            if (ontologyFormat instanceof PrefixDocumentFormat) {
                prefixManager = Optional.of(new DefaultPrefixManager());
                prefixManager.get().copyPrefixesFrom((PrefixDocumentFormat) ontologyFormat);
                prefixManager.get().setPrefixComparator(
                    ((PrefixDocumentFormat) ontologyFormat).getPrefixComparator());
                if (!o.isAnonymous() && prefixManager.get().getDefaultPrefix() == null) {
                    String existingDefault = prefixManager.get().getDefaultPrefix();
                    String ontologyIRIString = o.getOntologyID().getOntologyIRI().get().toString();
                    if (existingDefault == null || !existingDefault.startsWith(ontologyIRIString)) {
                        String defaultPrefix = ontologyIRIString;
                        if (!ontologyIRIString.endsWith("/") && !ontologyIRIString.endsWith("#")) {
                            defaultPrefix = ontologyIRIString + '#';
                        }
                        prefixManager.get().setDefaultPrefix(defaultPrefix);
                    }
                }
            }
            OWLOntologyManager manager = o.getOWLOntologyManager();
            OWLDataFactory df = manager.getOWLDataFactory();
            labelMaker = Optional.of(
                new AnnotationValueShortFormProvider(Collections.singletonList(df.getRDFSLabel()),
                    Collections.emptyMap(), manager, defaultPrefixManager));
        });
    }

    /**
     * Set the add missing declaration flag.
     *
     * @param flag new value
     */
    public void setAddMissingDeclarations(boolean flag) {
        addMissingDeclarations = flag;
    }

    /**
     * @param prefixManager the new prefix manager
     */
    public void setPrefixManager(PrefixManager prefixManager) {
        this.prefixManager = Optional.ofNullable(prefixManager);
        if (prefixManager instanceof DefaultPrefixManager) {
            defaultPrefixManager = (DefaultPrefixManager) prefixManager;
        }
    }

    protected void writePrefix(String prefix, String namespace) {
        write("Prefix");
        writeOpenBracket();
        write(prefix);
        write("=");
        write("<");
        write(namespace);
        write(">");
        writeCloseBracket();
        writeReturn();
    }

    protected void writePrefixes() {
        prefixManager.ifPresent(p -> p.getPrefixName2PrefixMap().forEach(this::writePrefix));
    }

    private void write(OWLXMLVocabulary v) {
        write(v.getShortForm());
    }

    private void write(String s) {
        try {
            writer.write(s);
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    private void flush() {
        try {
            writer.flush();
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    private void write(IRI iri) {
        String qname = prefixManager.map(p -> p.getPrefixIRIIgnoreQName(iri)).orElse(null);
        if (qname != null) {
            boolean lastCharIsColon = qname.charAt(qname.length() - 1) == ':';
            if (!lastCharIsColon) {
                write(qname);
                return;
            }
        }
        writeFullIRI(iri);
    }

    private void writeFullIRI(IRI iri) {
        write("<");
        write(iri.toString());
        write(">");
    }

    @Override
    public void visit(OWLOntology ontology) {
        writePrefixes();
        writeReturn();
        writeReturn();
        write(ONTOLOGY);
        writeOpenBracket();
        if (!ontology.isAnonymous()) {
            writeFullIRI(ontology.getOntologyID().getOntologyIRI().get());
            Optional<IRI> versionIRI = ontology.getOntologyID().getVersionIRI();
            if (versionIRI.isPresent()) {
                writeReturn();
                writeFullIRI(versionIRI.get());
            }
            writeReturn();
        }
        ontology.importsDeclarations().forEach(decl -> {
            write(IMPORT);
            writeOpenBracket();
            writeFullIRI(decl.getIRI());
            writeCloseBracket();
            writeReturn();
        });
        ontology.annotations().forEach(this::acceptAndReturn);
        writeReturn();
        Set<OWLAxiom> writtenAxioms = new HashSet<>();
        Collection<IRI> illegals = OWLDocumentFormat.determineIllegalPunnings(
            addMissingDeclarations, ontology.unsortedSignature(), ontology.getPunnedIRIs(INCLUDED));
        ontology.signature().forEach(e -> writeDeclarations(e, writtenAxioms, illegals));
        writeSortedEntities("Annotation Properties", "Annotation Property",
            ontology.annotationPropertiesInSignature(EXCLUDED), writtenAxioms);
        writeSortedEntities("Object Properties", "Object Property",
            ontology.objectPropertiesInSignature(EXCLUDED), writtenAxioms);
        writeSortedEntities("Data Properties", "Data Property",
            ontology.dataPropertiesInSignature(EXCLUDED), writtenAxioms);
        writeSortedEntities("Datatypes", "Datatype", ontology.datatypesInSignature(EXCLUDED),
            writtenAxioms);
        writeSortedEntities("Classes", "Class", ontology.classesInSignature(EXCLUDED),
            writtenAxioms);
        writeSortedEntities("Named Individuals", "Individual",
            ontology.individualsInSignature(EXCLUDED), writtenAxioms);
        ontology.signature().forEach(e -> writeAxioms(e, writtenAxioms));
        ontology.axioms().filter(ax -> !writtenAxioms.contains(ax)).sorted()
            .forEach(this::acceptAndReturn);
        writeCloseBracket();
        flush();
    }

    /**
     * @param axioms axioms to render
     * @return Stirng containing the axioms, rendered as if they were the only content of a fresh
     *         ontology
     */
    public String renderAxioms(Collection<OWLAxiom> axioms) {
        writePrefixes();
        writeReturn();
        writeReturn();
        write(ONTOLOGY);
        writeOpenBracket();
        writeReturn();
        Set<OWLAxiom> writtenAxioms = new HashSet<>();
        Set<OWLEntity> signature = asSet(axioms.stream().flatMap(OWLAxiom::signature));
        Collection<IRI> illegals = illegalPunnings(addMissingDeclarations, signature);
        signature.forEach(e -> writeDeclarations(e, writtenAxioms, illegals));
        Function<IRI, Stream<OWLAnnotationAssertionAxiom>> o = annotations(axioms);
        writeSortedEntities(o, "Annotation Properties", "Annotation Property",
            signature.stream().filter(OWLEntity::isOWLAnnotationProperty), writtenAxioms);
        writeSortedEntities(o, "Object Properties", "Object Property",
            signature.stream().filter(OWLEntity::isOWLObjectProperty), writtenAxioms);
        writeSortedEntities(o, "Data Properties", "Data Property",
            signature.stream().filter(OWLEntity::isOWLDataProperty), writtenAxioms);
        writeSortedEntities(o, "Datatypes", "Datatype",
            signature.stream().filter(OWLEntity::isOWLDatatype), writtenAxioms);
        writeSortedEntities(o, "Classes", "Class", signature.stream().filter(OWLEntity::isOWLClass),
            writtenAxioms);
        writeSortedEntities(o, "Named Individuals", "Individual",
            signature.stream().filter(OWLEntity::isIndividual), writtenAxioms);
        signature.forEach(e -> writeAxioms(e, writtenAxioms));
        axioms.stream().filter(ax -> !writtenAxioms.contains(ax)).sorted()
            .forEach(this::acceptAndReturn);
        writeCloseBracket();
        try {
            writer.flush();
        } catch (IOException e1) {
            throw new OWLRuntimeException(e1);
        }
        return writer.toString();
    }

    /**
     * @param add true if missing declarations should be added. If false, no declarations will be
     *        added.
     * @param signature signature to explore.
     * @return collection of IRIS used in illegal punnings
     */
    static Collection<IRI> illegalPunnings(boolean add, Collection<OWLEntity> signature) {
        if (!add) {
            return Collections.emptySet();
        }
        // determine what entities are illegally punned
        Map<IRI, List<EntityType<?>>> punnings = new HashMap<>();
        // disregard individuals as they do not give raise to illegal
        // punnings; only keep track of punned entities, ignore the rest
        Collection<IRI> punnedEntities = getPunnedIRIs(signature.stream().distinct());
        signature.stream().distinct()
            .filter(e -> !e.isOWLNamedIndividual() && punnedEntities.contains(e.getIRI()))
            .forEach(e -> punnings.computeIfAbsent(e.getIRI(), x -> new ArrayList<>())
                .add(e.getEntityType()));
        return computeIllegals(punnings);
    }

    /**
     * @param punnings input punnings
     * @return illegal punnings
     */
    static Collection<IRI> computeIllegals(Map<IRI, List<EntityType<?>>> punnings) {
        Collection<IRI> illegals = new HashSet<>();
        punnings.forEach((i, puns) -> computeIllegal(illegals, i, puns));
        return illegals;
    }

    /**
     * @param illegals set of illegal punnings
     * @param i iri to checl
     * @param puns list of pun types
     */
    static void computeIllegal(Collection<IRI> illegals, IRI i, List<EntityType<?>> puns) {
        boolean hasObject = puns.contains(EntityType.OBJECT_PROPERTY);
        boolean hasAnnotation = puns.contains(EntityType.ANNOTATION_PROPERTY);
        boolean hasData = puns.contains(EntityType.DATA_PROPERTY);
        if (hasObject && hasAnnotation || hasData && hasAnnotation || hasData && hasObject
            || puns.contains(EntityType.DATATYPE) && puns.contains(EntityType.CLASS)) {
            illegals.add(i);
        }
    }

    /**
     * Calculates the set of IRIs that are used for more than one entity type.
     *
     * @param signature signature to explore.
     * @return punned IRIs.
     */
    static Set<IRI> getPunnedIRIs(Stream<OWLEntity> signature) {
        Set<IRI> punned = new HashSet<>();
        Set<IRI> test = new HashSet<>();
        Predicate<IRI> tested = e -> !test.add(e);
        signature.map(OWLEntity::getIRI).filter(tested).forEach(punned::add);
        if (punned.isEmpty()) {
            return Collections.emptySet();
        }
        return punned;
    }

    protected Function<IRI, Stream<OWLAnnotationAssertionAxiom>> annotations(
        Collection<OWLAxiom> axioms) {
        return x -> axioms.stream().filter(ax -> annotations(ax, x))
            .map(OWLAnnotationAssertionAxiom.class::cast);
    }

    boolean annotations(OWLAxiom ax, IRI i) {
        return ax instanceof OWLAnnotationAssertionAxiom
            && ((OWLAnnotationAssertionAxiom) ax).getSubject().equals(i);
    }

    private void writeSortedEntities(String bannerComment, String entityTypeName,
        Stream<? extends OWLEntity> entities, Set<OWLAxiom> writtenAxioms) {
        List<? extends OWLEntity> sortOptionally = asList(entities.sorted());
        if (!sortOptionally.isEmpty()) {
            writeEntities(bannerComment, entityTypeName, sortOptionally, writtenAxioms);
            writeReturn();
        }
    }

    private void writeln(String s) {
        write(s);
        writeReturn();
    }

    private void writeEntities(String comment, String entityTypeName,
        List<? extends OWLEntity> entities, Set<OWLAxiom> writtenAxioms) {
        boolean haveWrittenBanner = false;
        for (OWLEntity owlEntity : entities) {
            List<? extends OWLAxiom> axiomsForEntity =
                asList(retrieve(owlEntity).filter(ax -> !writtenAxioms.contains(ax)));
            List<OWLAnnotationAssertionAxiom> list =
                asList(ont.map(o -> o.annotationAssertionAxioms(owlEntity.getIRI())
                    .filter(ax -> !writtenAxioms.contains(ax))).orElse(Stream.empty()));
            if (axiomsForEntity.isEmpty() && list.isEmpty()) {
                continue;
            }
            if (!haveWrittenBanner) {
                writeln("############################");
                writeln("#   " + comment);
                writeln("############################");
                writeReturn();
                haveWrittenBanner = true;
            }
            axiomsForEntity.sort(null);
            list.sort(null);
            writeEntity2(owlEntity, entityTypeName, axiomsForEntity, list, writtenAxioms);
        }
    }

    private void writeSortedEntities(Function<IRI, Stream<OWLAnnotationAssertionAxiom>> annotations,
        String bannerComment, String entityTypeName, Stream<? extends OWLEntity> entities,
        Set<OWLAxiom> writtenAxioms) {
        List<? extends OWLEntity> sortOptionally = asList(entities.sorted());
        if (!sortOptionally.isEmpty()) {
            writeEntities(annotations, bannerComment, entityTypeName, sortOptionally,
                writtenAxioms);
            writeReturn();
        }
    }

    private void writeEntities(Function<IRI, Stream<OWLAnnotationAssertionAxiom>> annotations,
        String comment, String entityTypeName, List<? extends OWLEntity> entities,
        Set<OWLAxiom> writtenAxioms) {
        boolean haveWrittenBanner = false;
        for (OWLEntity owlEntity : entities) {
            List<? extends OWLAxiom> axiomsForEntity =
                asList(retrieve(owlEntity).filter(ax -> !writtenAxioms.contains(ax)));
            List<OWLAnnotationAssertionAxiom> list = asList(
                annotations.apply(owlEntity.getIRI()).filter(ax -> !writtenAxioms.contains(ax)));
            if (axiomsForEntity.isEmpty() && list.isEmpty()) {
                continue;
            }
            if (!haveWrittenBanner) {
                write("############################\n#   " + comment
                    + "\n############################\n\n");
                haveWrittenBanner = true;
            }
            axiomsForEntity.sort(null);
            list.sort(null);
            writeEntity2(owlEntity, entityTypeName, axiomsForEntity, list, writtenAxioms);
        }
    }

    private Stream<? extends OWLAxiom> retrieve(OWLEntity entity) {
        return ont.map(o -> retrieve(entity, o)).orElse(Stream.empty());
    }

    /**
     * Writes out the axioms that define the specified entity.
     *
     * @param entity The entity
     * @return The set of axioms that was written out
     */
    protected Set<OWLAxiom> writeAxioms(OWLEntity entity) {
        Set<OWLAxiom> writtenAxioms = new HashSet<>();
        writeAxioms(entity, writtenAxioms);
        return writtenAxioms;
    }

    /**
     * Writes out the axioms that define the specified entity.
     *
     * @param entity The entity
     * @return The set of axioms that was written out
     */
    protected Set<OWLAxiom> writeEntity(OWLEntity entity) {
        Set<OWLAxiom> writtenAxioms = new HashSet<>();
        writeEntity(entity, writtenAxioms);
        return writtenAxioms;
    }

    protected void writeEntity(OWLEntity entity, Set<OWLAxiom> alreadyWrittenAxioms) {
        writeEntity2(entity, "", asList(retrieve(entity).sorted()),
            asList(ont.map(o -> o.annotationAssertionAxioms(entity.getIRI()).sorted())
                .orElse(Stream.empty())),
            alreadyWrittenAxioms);
    }

    protected void writeEntity2(OWLEntity entity, String entityTypeName,
        List<? extends OWLAxiom> axiomsForEntity,
        List<OWLAnnotationAssertionAxiom> annotationAssertionAxioms,
        Set<OWLAxiom> alreadyWrittenAxioms) {
        writeln("# " + entityTypeName + ": " + getIRIString(entity) + " ("
            + getEntityLabel(entity, annotationAssertionAxioms) + ")");
        writeReturn();
        annotationAssertionAxioms.stream().filter(alreadyWrittenAxioms::add)
            .forEach(this::acceptAndReturn);
        axiomsForEntity.stream().filter(this::shouldWrite).filter(alreadyWrittenAxioms::add)
            .forEach(this::acceptAndReturn);
        writeReturn();
    }

    private boolean shouldWrite(OWLAxiom ax) {
        if (ax.getAxiomType().equals(AxiomType.DIFFERENT_INDIVIDUALS)) {
            return false;
        }
        if (ax.getAxiomType().equals(AxiomType.DISJOINT_CLASSES)
            && ((OWLDisjointClassesAxiom) ax).classExpressions().count() > 2) {
            return false;
        }
        return true;
    }

    private String getIRIString(OWLEntity entity) {
        return defaultPrefixManager.getShortForm(entity);
    }

    private String getEntityLabel(OWLEntity entity, List<OWLAnnotationAssertionAxiom> axioms) {
        if (labelMaker.isPresent()) {
            return labelMaker.get().getShortForm(entity).replace("\n", "\n# ");
        }
        labelMaker = Optional
            .of(new ShortFormFromRDFSLabelAxiomListProvider(Collections.emptyList(), axioms));
        return labelMaker.get().getShortForm(entity).replace("\n", "\n# ");
    }

    private void writeAxioms(OWLEntity entity, Set<OWLAxiom> alreadyWrittenAxioms) {
        writeAnnotations(entity, alreadyWrittenAxioms);
        List<OWLAxiom> writtenAxioms = new ArrayList<>();
        Stream<? extends OWLAxiom> stream = retrieve(entity).filter(alreadyWrittenAxioms::contains)
            .filter(ax -> ax.getAxiomType().equals(AxiomType.DIFFERENT_INDIVIDUALS))
            .filter(ax -> ax.getAxiomType().equals(AxiomType.DISJOINT_CLASSES)
                && ((OWLDisjointClassesAxiom) ax).classExpressions().count() > 2)
            .sorted();
        stream.forEach(ax -> {
            ax.accept(this);
            writtenAxioms.add(ax);
            writeReturn();
        });
        alreadyWrittenAxioms.addAll(writtenAxioms);
    }

    /**
     * Writes out the declaration axioms for the specified entity.
     *
     * @param entity The entity
     * @return The axioms that were written out
     */
    protected Set<OWLAxiom> writeDeclarations(OWLEntity entity) {
        Set<OWLAxiom> axioms = new HashSet<>();
        ont.map(o -> o.declarationAxioms(entity).sorted()).orElse(Stream.empty()).forEach(ax -> {
            ax.accept(this);
            axioms.add(ax);
            writeReturn();
        });
        return axioms;
    }

    private void writeDeclarations(OWLEntity entity, Set<OWLAxiom> alreadyWrittenAxioms,
        Collection<IRI> illegals) {
        ont.ifPresent(o -> {
            Collection<OWLDeclarationAxiom> axioms = asList(o.declarationAxioms(entity).sorted());
            axioms.stream().filter(alreadyWrittenAxioms::add).forEach(this::acceptAndReturn);
            // if multiple illegal declarations already exist, they have already
            // been outputted the renderer cannot take responsibility for removing
            // them. It should not add declarations for illegally punned entities
            // here, though
            if (addMissingDeclarations && axioms.isEmpty() && !entity.isBuiltIn()
                && !illegals.contains(entity.getIRI()) && !o.isDeclared(entity, Imports.INCLUDED)) {
                OWLDeclarationAxiom declaration =
                    o.getOWLOntologyManager().getOWLDataFactory().getOWLDeclarationAxiom(entity);
                acceptAndReturn(declaration);
            }
        });
    }

    protected void acceptAndReturn(OWLObject ax) {
        ax.accept(this);
        writeReturn();
    }

    protected void acceptAndSpace(OWLObject ax) {
        ax.accept(this);
        writeSpace();
    }

    protected void spaceAndAccept(OWLObject ax) {
        writeSpace();
        ax.accept(this);
    }

    /**
     * Writes of the annotation for the specified entity.
     *
     * @param entity The entity
     * @param alreadyWrittenAxioms already written axioms, to be updated with the newly written
     *        axioms
     */
    protected void writeAnnotations(OWLEntity entity, Set<OWLAxiom> alreadyWrittenAxioms) {
        ont.ifPresent(o -> o.annotationAssertionAxioms(entity.getIRI()).sorted()
            .filter(alreadyWrittenAxioms::add).forEach(this::acceptAndReturn));
    }

    /**
     * Write.
     *
     * @param v the v
     * @param o the o
     */
    protected void write(OWLXMLVocabulary v, OWLObject o) {
        write(v);
        writeOpenBracket();
        o.accept(this);
        writeCloseBracket();
    }

    private void write(Stream<? extends OWLObject> objects) {
        write(asList(objects));
    }

    private void write(List<? extends OWLObject> objects) {
        for (int i = 0; i < objects.size(); i++) {
            if (i > 0) {
                writeSpace();
            }
            objects.get(i).accept(this);
        }
    }

    protected void writeOpenBracket() {
        write("(");
    }

    protected void writeCloseBracket() {
        write(")");
    }

    protected void writeSpace() {
        write(" ");
    }

    protected void writeReturn() {
        write("\n");
    }

    protected void writeAnnotations(OWLAxiom ax) {
        ax.annotations().forEach(this::acceptAndSpace);
    }

    protected void writeAxiomStart(OWLXMLVocabulary v, OWLAxiom axiom) {
        write(v);
        writeOpenBracket();
        writeAnnotations(axiom);
    }

    protected void writeAxiomEnd() {
        writeCloseBracket();
    }

    protected void writePropertyCharacteristic(OWLXMLVocabulary v, OWLAxiom ax,
        OWLPropertyExpression prop) {
        writeAxiomStart(v, ax);
        prop.accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(ASYMMETRIC_OBJECT_PROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        writeAxiomStart(CLASS_ASSERTION, axiom);
        acceptAndSpace(axiom.getClassExpression());
        axiom.getIndividual().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        writeAxiomStart(DATA_PROPERTY_ASSERTION, axiom);
        acceptAndSpace(axiom.getProperty());
        acceptAndSpace(axiom.getSubject());
        axiom.getObject().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        writeAxiomStart(DATA_PROPERTY_DOMAIN, axiom);
        acceptAndSpace(axiom.getProperty());
        axiom.getDomain().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        writeAxiomStart(DATA_PROPERTY_RANGE, axiom);
        acceptAndSpace(axiom.getProperty());
        axiom.getRange().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        writeAxiomStart(SUB_DATA_PROPERTY_OF, axiom);
        acceptAndSpace(axiom.getSubProperty());
        axiom.getSuperProperty().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        writeAxiomStart(DECLARATION, axiom);
        writeEntitiesAsURIs = false;
        axiom.getEntity().accept(this);
        writeEntitiesAsURIs = true;
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        List<OWLIndividual> individuals = asList(axiom.individuals());
        if (individuals.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(DIFFERENT_INDIVIDUALS, axiom);
        write(individuals);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        List<OWLClassExpression> classExpressions = asList(axiom.classExpressions());
        if (classExpressions.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(DISJOINT_CLASSES, axiom);
        write(classExpressions);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        List<OWLDataPropertyExpression> properties = asList(axiom.properties());
        if (properties.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(DISJOINT_DATA_PROPERTIES, axiom);
        write(properties);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        List<OWLObjectPropertyExpression> properties = asList(axiom.properties());
        if (properties.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(DISJOINT_OBJECT_PROPERTIES, axiom);
        write(properties);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        writeAxiomStart(DISJOINT_UNION, axiom);
        acceptAndSpace(axiom.getOWLClass());
        write(axiom.classExpressions());
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {
        writeAxiomStart(ANNOTATION_ASSERTION, axiom);
        acceptAndSpace(axiom.getProperty());
        acceptAndSpace(axiom.getSubject());
        axiom.getValue().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        List<OWLClassExpression> classExpressions = asList(axiom.classExpressions());
        if (classExpressions.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(EQUIVALENT_CLASSES, axiom);
        write(classExpressions);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        List<OWLDataPropertyExpression> properties = asList(axiom.properties());
        if (properties.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(EQUIVALENT_DATA_PROPERTIES, axiom);
        write(properties);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        List<OWLObjectPropertyExpression> properties = asList(axiom.properties());
        if (properties.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(EQUIVALENT_OBJECT_PROPERTIES, axiom);
        write(properties);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        writePropertyCharacteristic(FUNCTIONAL_DATA_PROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(FUNCTIONAL_OBJECT_PROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(INVERSE_FUNCTIONAL_OBJECT_PROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        writeAxiomStart(INVERSE_OBJECT_PROPERTIES, axiom);
        acceptAndSpace(axiom.getFirstProperty());
        axiom.getSecondProperty().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(IRREFLEXIVE_OBJECT_PROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        writeAxiomStart(NEGATIVE_DATA_PROPERTY_ASSERTION, axiom);
        acceptAndSpace(axiom.getProperty());
        acceptAndSpace(axiom.getSubject());
        axiom.getObject().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        writeAxiomStart(NEGATIVE_OBJECT_PROPERTY_ASSERTION, axiom);
        acceptAndSpace(axiom.getProperty());
        acceptAndSpace(axiom.getSubject());
        axiom.getObject().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        writeAxiomStart(OBJECT_PROPERTY_ASSERTION, axiom);
        acceptAndSpace(axiom.getProperty());
        acceptAndSpace(axiom.getSubject());
        axiom.getObject().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        writeAxiomStart(SUB_OBJECT_PROPERTY_OF, axiom);
        write(OBJECT_PROPERTY_CHAIN);
        writeOpenBracket();
        write(axiom.getPropertyChain());
        writeCloseBracket();
        spaceAndAccept(axiom.getSuperProperty());
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        writeAxiomStart(OBJECT_PROPERTY_DOMAIN, axiom);
        acceptAndSpace(axiom.getProperty());
        axiom.getDomain().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        writeAxiomStart(OBJECT_PROPERTY_RANGE, axiom);
        acceptAndSpace(axiom.getProperty());
        axiom.getRange().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        writeAxiomStart(SUB_OBJECT_PROPERTY_OF, axiom);
        acceptAndSpace(axiom.getSubProperty());
        axiom.getSuperProperty().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(REFLEXIVE_OBJECT_PROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        List<OWLIndividual> individuals = axiom.getIndividualsAsList();
        if (individuals.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(SAME_INDIVIDUAL, axiom);
        write(individuals);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        writeAxiomStart(SUB_CLASS_OF, axiom);
        acceptAndSpace(axiom.getSubClass());
        axiom.getSuperClass().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(SYMMETRIC_OBJECT_PROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(TRANSITIVE_OBJECT_PROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(OWLClass ce) {
        if (!writeEntitiesAsURIs) {
            write(CLASS);
            writeOpenBracket();
        }
        ce.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    private <F extends OWLPropertyRange> void writeRestriction(OWLXMLVocabulary v,
        OWLCardinalityRestriction<F> restriction, OWLPropertyExpression p) {
        write(v);
        writeOpenBracket();
        write(Integer.toString(restriction.getCardinality()));
        spaceAndAccept(p);
        if (restriction.isQualified()) {
            spaceAndAccept(restriction.getFiller());
        }
        writeCloseBracket();
    }

    private void writeRestriction(OWLXMLVocabulary v, OWLQuantifiedDataRestriction restriction) {
        writeRestriction(v, restriction.getProperty(), restriction.getFiller());
    }

    private void writeRestriction(OWLXMLVocabulary v, OWLQuantifiedObjectRestriction restriction) {
        writeRestriction(v, restriction.getProperty(), restriction.getFiller());
    }

    private void writeRestriction(OWLXMLVocabulary v, OWLPropertyExpression prop,
        OWLObject filler) {
        write(v);
        writeOpenBracket();
        acceptAndSpace(prop);
        filler.accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(OWLDataAllValuesFrom ce) {
        writeRestriction(DATA_ALL_VALUES_FROM, ce);
    }

    @Override
    public void visit(OWLDataExactCardinality ce) {
        writeRestriction(DATA_EXACT_CARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(OWLDataMaxCardinality ce) {
        writeRestriction(DATA_MAX_CARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(OWLDataMinCardinality ce) {
        writeRestriction(DATA_MIN_CARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        writeRestriction(DATA_SOME_VALUES_FROM, ce);
    }

    @Override
    public void visit(OWLDataHasValue ce) {
        writeRestriction(DATA_HAS_VALUE, ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        writeRestriction(OBJECT_ALL_VALUES_FROM, ce);
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        write(OBJECT_COMPLEMENT_OF, ce.getOperand());
    }

    @Override
    public void visit(OWLObjectExactCardinality ce) {
        writeRestriction(OBJECT_EXACT_CARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(OWLObjectIntersectionOf ce) {
        if (ce.operands().count() == 1) {
            ce.operands().forEach(x -> x.accept(this));
            return;
        }
        write(OBJECT_INTERSECTION_OF);
        writeOpenBracket();
        write(ce.operands());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLObjectMaxCardinality ce) {
        writeRestriction(OBJECT_MAX_CARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(OWLObjectMinCardinality ce) {
        writeRestriction(OBJECT_MIN_CARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(OWLObjectOneOf ce) {
        write(OBJECT_ONE_OF);
        writeOpenBracket();
        write(ce.individuals());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLObjectHasSelf ce) {
        write(OBJECT_HAS_SELF, ce.getProperty());
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        writeRestriction(OBJECT_SOME_VALUES_FROM, ce);
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        if (ce.operands().count() == 1) {
            ce.operands().forEach(x -> x.accept(this));
            return;
        }
        write(OBJECT_UNION_OF);
        writeOpenBracket();
        write(ce.operands());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLObjectHasValue ce) {
        writeRestriction(OBJECT_HAS_VALUE, ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLDataComplementOf node) {
        write(DATA_COMPLEMENT_OF, node.getDataRange());
    }

    @Override
    public void visit(OWLDataOneOf node) {
        write(DATA_ONE_OF);
        writeOpenBracket();
        write(node.values());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLDatatype node) {
        if (!writeEntitiesAsURIs) {
            write(DATATYPE);
            writeOpenBracket();
        }
        node.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        write(DATATYPE_RESTRICTION);
        writeOpenBracket();
        node.getDatatype().accept(this);
        node.facetRestrictions().forEach(this::spaceAndAccept);
        writeCloseBracket();
    }

    @Override
    public void visit(OWLFacetRestriction node) {
        write(node.getFacet().getIRI());
        spaceAndAccept(node.getFacetValue());
    }

    @Override
    public void visit(OWLLiteral node) {
        write("\"");
        write(EscapeUtils.escapeString(node.getLiteral()));
        write("\"");
        if (node.hasLang()) {
            write("@");
            write(node.getLang());
        } else if (!node.isRDFPlainLiteral()
            && !OWL2Datatype.XSD_STRING.matches(node.getDatatype())) {
            write("^^");
            write(node.getDatatype().getIRI());
        }
    }

    @Override
    public void visit(OWLDataProperty property) {
        if (!writeEntitiesAsURIs) {
            write(DATA_PROPERTY);
            writeOpenBracket();
        }
        property.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    @Override
    public void visit(OWLObjectProperty property) {
        if (!writeEntitiesAsURIs) {
            write(OBJECT_PROPERTY);
            writeOpenBracket();
        }
        property.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    @Override
    public void visit(OWLObjectInverseOf property) {
        write(OBJECT_INVERSE_OF);
        writeOpenBracket();
        property.getInverse().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        if (!writeEntitiesAsURIs) {
            write(NAMED_INDIVIDUAL);
            writeOpenBracket();
        }
        individual.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    @Override
    public void visit(OWLHasKeyAxiom axiom) {
        writeAxiomStart(HAS_KEY, axiom);
        acceptAndSpace(axiom.getClassExpression());
        writeOpenBracket();
        write(asList(axiom.objectPropertyExpressions()));
        writeCloseBracket();
        writeSpace();
        writeOpenBracket();
        write(asList(axiom.dataPropertyExpressions()));
        writeCloseBracket();
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        writeAxiomStart(ANNOTATION_PROPERTY_DOMAIN, axiom);
        acceptAndSpace(axiom.getProperty());
        axiom.getDomain().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        writeAxiomStart(ANNOTATION_PROPERTY_RANGE, axiom);
        acceptAndSpace(axiom.getProperty());
        axiom.getRange().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        writeAxiomStart(SUB_ANNOTATION_PROPERTY_OF, axiom);
        acceptAndSpace(axiom.getSubProperty());
        axiom.getSuperProperty().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        if (node.operands().count() == 1) {
            node.operands().forEach(x -> x.accept(this));
            return;
        }
        write(DATA_INTERSECTION_OF);
        writeOpenBracket();
        write(node.operands());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        if (node.operands().count() == 1) {
            node.operands().forEach(x -> x.accept(this));
            return;
        }
        write(DATA_UNION_OF);
        writeOpenBracket();
        write(node.operands());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLAnnotationProperty property) {
        if (!writeEntitiesAsURIs) {
            write(ANNOTATION_PROPERTY);
            writeOpenBracket();
        }
        property.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {
        write(individual.getID().toString());
    }

    @Override
    public void visit(IRI iri) {
        write(iri);
    }

    @Override
    public void visit(OWLAnnotation node) {
        write(ANNOTATION);
        writeOpenBracket();
        node.annotations().forEach(this::acceptAndSpace);
        acceptAndSpace(node.getProperty());
        node.getValue().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        writeAxiomStart(DATATYPE_DEFINITION, axiom);
        acceptAndSpace(axiom.getDatatype());
        axiom.getDataRange().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(SWRLRule rule) {
        writeAxiomStart(DL_SAFE_RULE, rule);
        write(BODY);
        writeOpenBracket();
        write(rule.body());
        writeCloseBracket();
        write(HEAD);
        writeOpenBracket();
        write(rule.head());
        writeCloseBracket();
        writeAxiomEnd();
    }

    @Override
    public void visit(SWRLIndividualArgument node) {
        node.getIndividual().accept(this);
    }

    @Override
    public void visit(SWRLClassAtom node) {
        write(CLASS_ATOM);
        writeOpenBracket();
        acceptAndSpace(node.getPredicate());
        node.getArgument().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(SWRLDataRangeAtom node) {
        write(DATA_RANGE_ATOM);
        writeOpenBracket();
        acceptAndSpace(node.getPredicate());
        node.getArgument().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(SWRLObjectPropertyAtom node) {
        write(OBJECT_PROPERTY_ATOM);
        writeOpenBracket();
        acceptAndSpace(node.getPredicate());
        acceptAndSpace(node.getFirstArgument());
        node.getSecondArgument().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(SWRLDataPropertyAtom node) {
        write(DATA_PROPERTY_ATOM);
        writeOpenBracket();
        acceptAndSpace(node.getPredicate());
        acceptAndSpace(node.getFirstArgument());
        node.getSecondArgument().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(SWRLBuiltInAtom node) {
        write(BUILT_IN_ATOM);
        writeOpenBracket();
        acceptAndSpace(node.getPredicate());
        write(node.getArguments());
        writeCloseBracket();
    }

    @Override
    public void visit(SWRLVariable node) {
        write(VARIABLE);
        writeOpenBracket();
        node.getIRI().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(SWRLLiteralArgument node) {
        node.getLiteral().accept(this);
    }

    @Override
    public void visit(SWRLDifferentIndividualsAtom node) {
        write(DIFFERENT_INDIVIDUALS_ATOM);
        writeOpenBracket();
        acceptAndSpace(node.getFirstArgument());
        node.getSecondArgument().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(SWRLSameIndividualAtom node) {
        write(SAME_INDIVIDUAL_ATOM);
        writeOpenBracket();
        acceptAndSpace(node.getFirstArgument());
        node.getSecondArgument().accept(this);
        writeCloseBracket();
    }
}
