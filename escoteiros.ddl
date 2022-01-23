CREATE TABLE item (
    id                         NUMBER(2) GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
    name                       VARCHAR2(50 CHAR) NOT NULL,
    description                VARCHAR2(255 CHAR),
    purchaseat                 DATE,
    endoflife                  DATE,
    id_code                    VARCHAR2(11) NOT NULL,
    sub_section_internal_code  NUMBER(2),
    item_categories_id         NUMBER(1) NOT NULL
);

ALTER TABLE item ADD CONSTRAINT item_pk PRIMARY KEY ( id );

CREATE TABLE item_categories (
    id        NUMBER(1) GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
    category  VARCHAR2(30 CHAR)
);

ALTER TABLE item_categories ADD CONSTRAINT item_categories_pk PRIMARY KEY ( id );

CREATE TABLE item_inspections (
    id           NUMBER(11) GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
    "date"       DATE NOT NULL,
    description  VARCHAR2(255 CHAR) NOT NULL,
    item_id      NUMBER(2)
);

ALTER TABLE item_inspections ADD CONSTRAINT item_inspections_pk PRIMARY KEY ( id );

CREATE TABLE section (
    id       NUMBER(1) NOT NULL,
    section  VARCHAR2(12 CHAR) NOT NULL
);

ALTER TABLE section ADD CONSTRAINT section_pk PRIMARY KEY ( id );

CREATE TABLE sub_section (
    internal_code  NUMBER(2) GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
    id             NUMBER(2) NOT NULL,
    subsection     VARCHAR2(60 CHAR) NOT NULL,
    section_id     NUMBER(1)
);

ALTER TABLE sub_section ADD CONSTRAINT sub_section_pk PRIMARY KEY ( internal_code );

ALTER TABLE item_inspections
    ADD CONSTRAINT item_inspections_item_fk FOREIGN KEY ( item_id )
        REFERENCES item ( id )
            ON DELETE CASCADE;

ALTER TABLE item
    ADD CONSTRAINT item_item_categories_fk FOREIGN KEY ( item_categories_id )
        REFERENCES item_categories ( id )
            ON DELETE CASCADE;

ALTER TABLE item
    ADD CONSTRAINT item_sub_section_fk FOREIGN KEY ( sub_section_internal_code )
        REFERENCES sub_section ( internal_code )
            ON DELETE CASCADE;

ALTER TABLE sub_section
    ADD CONSTRAINT sub_section_section_fk FOREIGN KEY ( section_id )
        REFERENCES section ( id )
            ON DELETE CASCADE;


-- Procedures

-- Sections

CREATE OR REPLACE PROCEDURE getSections(cursorSec OUT SYS_REFCURSOR) IS
BEGIN
    OPEN cursorSec FOR SELECT * from section ORDER BY id;
END;
/

CREATE OR REPLACE PROCEDURE getSectionName(cursorSec OUT SYS_REFCURSOR, s_id IN section.id%TYPE) IS
BEGIN
    OPEN cursorSec FOR SELECT section.section FROM section WHERE id=s_id;
END;
/
    
CREATE OR REPLACE PROCEDURE addSections(s_id IN section.id%TYPE, s_section IN section.section%TYPE) IS 
BEGIN
    INSERT INTO section(id, section) VALUES (s_id, s_section);
    COMMIT;
END;   
/

CREATE OR REPLACE PROCEDURE updateSections(s_id IN section.id%TYPE, s_section IN section.section%TYPE) IS 
BEGIN
    UPDATE section SET id=s_id, section=s_section  WHERE ID = s_id;
    COMMIT;
END;   
/

CREATE OR REPLACE PROCEDURE deleteSections(s_id IN section.id%TYPE) IS 
BEGIN
    DELETE FROM section WHERE ID = s_id;
    COMMIT;
END;   
/

-- SubSections

CREATE OR REPLACE PROCEDURE getSubSections(cursorSubSec OUT SYS_REFCURSOR) IS
BEGIN 
    OPEN cursorSubSec FOR SELECT * from sub_section ORDER BY internal_code;
END;
/

CREATE OR REPLACE PROCEDURE addSubSections(ss_id IN sub_section.id%TYPE, ss_subsection IN sub_section.subsection%TYPE, ss_sectionId IN sub_section.section_id%TYPE, ss_internalCode OUT sub_section.internal_code%TYPE) IS 
BEGIN
    INSERT INTO sub_section(id, subsection, section_id) VALUES (ss_id, ss_subsection, ss_sectionId) RETURNING internal_code INTO ss_internalCode;
    COMMIT;
END;   
/

CREATE OR REPLACE PROCEDURE updateSubSections(ss_internalCode IN sub_section.internal_code%TYPE, ss_id IN sub_section.id%TYPE, ss_subsection IN sub_section.subsection%TYPE, ss_sectionId IN sub_section.section_id%TYPE) IS 
BEGIN
    UPDATE sub_section SET id=ss_id, subsection=ss_subsection, section_id=ss_sectionId  WHERE internal_code = ss_internalCode;
    COMMIT;
END;   
/

CREATE OR REPLACE PROCEDURE deleteSubSections(ss_internalCode IN sub_section.internal_code%TYPE) IS 
BEGIN
    DELETE FROM sub_section WHERE internal_code = ss_internalCode;
    COMMIT;
END;   
/

-- Items

CREATE OR REPLACE PROCEDURE getItems (cursorItem OUT SYS_REFCURSOR) IS
BEGIN
   OPEN cursorItem FOR SELECT * from item ORDER BY id;
END;
/

CREATE OR REPLACE PROCEDURE addItem(i_name IN item.name%TYPE, i_description IN item.description%TYPE, i_purchaseat IN item.purchaseat%TYPE, i_endoflife IN item.endoflife%TYPE, i_idCode IN item.id_code%TYPE, i_subSectionInternalCode IN item.sub_section_internal_code%TYPE, i_itemCategoriesId IN item.item_categories_id%TYPE, i_id OUT item.id%TYPE) IS
BEGIN
    INSERT INTO item(name, description, purchaseat, endoflife, id_code, sub_section_internal_code, item_categories_id) VALUES (i_name, i_description, i_purchaseat, i_endoflife, i_idCode, i_subSectionInternalCode, i_itemCategoriesId) RETURNING id INTO i_id;
    UPDATE item SET id_code=i_idCode||i_id  WHERE ID = i_id;
    COMMIT;
END;   
/

CREATE OR REPLACE PROCEDURE addItems(i_name IN item.name%TYPE, i_description IN item.description%TYPE, i_purchaseat IN item.purchaseat%TYPE, i_endoflife IN item.endoflife%TYPE, i_idCode IN item.id_code%TYPE, i_subSectionInternalCode IN item.sub_section_internal_code%TYPE, i_itemCategoriesId IN item.item_categories_id%TYPE, i_id OUT item.id%TYPE) IS
BEGIN
    INSERT INTO item(name, description, purchaseat, endoflife, id_code, sub_section_internal_code, item_categories_id) VALUES (i_name, i_description, i_purchaseat, i_endoflife, i_idCode, i_subSectionInternalCode, i_itemCategoriesId) RETURNING id INTO i_id;
    COMMIT;
END;   
/

CREATE OR REPLACE PROCEDURE updateItems(i_id IN item.id%TYPE, i_name IN item.name%TYPE, i_description IN item.description%TYPE, i_purchaseat IN item.purchaseat%TYPE, i_endoflife IN item.endoflife%TYPE, i_idCode IN item.id_code%TYPE, i_subSectionInternalCode IN item.sub_section_internal_code%TYPE, i_itemCategoriesId IN item.item_categories_id%TYPE) IS 
BEGIN
    UPDATE item SET name=i_name, description=i_description, purchaseat=i_purchaseat, endoflife=i_endoflife, id_code=i_idCode, sub_section_internal_code=i_subSectionInternalCode, item_categories_id=i_itemCategoriesId WHERE ID = i_id;
    COMMIT;
END;   
/

CREATE OR REPLACE PROCEDURE deleteItems(i_id IN item.id%TYPE) IS 
BEGIN
    DELETE FROM item WHERE ID = i_id;
    COMMIT;
END;   
/

-- Categories

CREATE OR REPLACE PROCEDURE getItemCategories(cursorItemCat OUT SYS_REFCURSOR) IS
BEGIN
    OPEN cursorItemCat FOR SELECT * from item_categories ORDER BY id;
END;
/

CREATE OR REPLACE PROCEDURE addItemCategories(ic_category IN item_categories.category%TYPE, ic_id OUT item_categories.id%TYPE) IS 
BEGIN
    INSERT INTO item_categories(category) VALUES (ic_category) RETURNING id INTO ic_id;
    COMMIT;
END;   
/

CREATE OR REPLACE PROCEDURE addItemCategory(ic_category IN item_categories.category%TYPE, ic_id OUT item_categories.id%TYPE) IS 
BEGIN
    INSERT INTO item_categories(category) VALUES (ic_category) RETURNING id INTO ic_id;
    COMMIT;
END;   
/

CREATE OR REPLACE PROCEDURE updateItemCategories(ic_id IN item_categories.id%TYPE, ic_category IN item_categories.category%TYPE) IS 
BEGIN
    UPDATE item_categories SET category=ic_category WHERE ID = ic_id;
    COMMIT;
END;   
/

CREATE OR REPLACE PROCEDURE deleteItemCategories(ic_id IN item_categories.id%TYPE) IS 
BEGIN
    DELETE FROM item_categories WHERE ID = ic_id;
    COMMIT;
END;   
/

-- Item Inspections

CREATE OR REPLACE PROCEDURE getItemInspections(cursorItemInsp OUT SYS_REFCURSOR) IS
BEGIN
    OPEN cursorItemInsp FOR SELECT * from item_inspections ORDER BY id;
END;
/

CREATE OR REPLACE PROCEDURE addItemInspections(ii_date IN item_inspections."date"%TYPE, ii_description IN item_inspections.description%TYPE, ii_itemId IN item_inspections.item_id%TYPE, ii_id OUT item_inspections.id%TYPE) IS 
BEGIN
    INSERT INTO item_inspections("date", description, item_id) VALUES (ii_date, ii_description, ii_itemId) RETURNING id INTO  ii_id;
    COMMIT;
END;   
/

CREATE OR REPLACE PROCEDURE updateItemInspections(ii_id IN item_inspections.id%TYPE, ii_date IN item_inspections."date"%TYPE, ii_description IN item_inspections.description%TYPE, ii_item_id IN item_inspections.item_id%TYPE) IS 
BEGIN
    UPDATE item_inspections SET "date"=ii_date, description=ii_description, item_id=ii_item_id WHERE ID = ii_id;
    COMMIT;
END;   
/

CREATE OR REPLACE PROCEDURE deleteItemInspections(ii_id IN item_inspections.id%TYPE) IS 
BEGIN
    DELETE FROM item_inspections WHERE ID = ii_id;
    COMMIT;
END;   
/

-- Truncates

CREATE OR REPLACE PROCEDURE deleteSectionsData IS
BEGIN
    EXECUTE IMMEDIATE 'truncate table section';
END;
/

CREATE OR REPLACE PROCEDURE deleteSubSectionsData IS
BEGIN
    EXECUTE IMMEDIATE 'TRUNCATE TABLE sub_section';
END;
/

CREATE OR REPLACE PROCEDURE deleteItemCategoriesData IS
BEGIN
    EXECUTE IMMEDIATE 'TRUNCATE TABLE item_categories';
END;
/

CREATE OR REPLACE PROCEDURE deleteItemsData IS
BEGIN
    EXECUTE IMMEDIATE 'TRUNCATE TABLE item';
END;
/

CREATE OR REPLACE PROCEDURE deleteItemInspectionsData IS
BEGIN
    EXECUTE IMMEDIATE 'TRUNCATE TABLE item_inspections';
END;
/

-- Materiais Procedures

-- SubSections

CREATE OR REPLACE PROCEDURE getAllSubSectionItems (cursorSubSectionItem OUT SYS_REFCURSOR) IS
BEGIN
   OPEN cursorSubSectionItem FOR SELECT * FROM item WHERE sub_section_internal_code IN (SELECT internal_code FROM sub_section WHERE id!=0) ORDER BY id;
END;
/

CREATE OR REPLACE PROCEDURE getAllSubSectionSpecificItems (cursorSubSectionSpecificItem OUT SYS_REFCURSOR, ssi_subSectionInternalCode IN item.sub_section_internal_code%TYPE) IS
BEGIN
   OPEN cursorSubSectionSpecificItem FOR SELECT * FROM item WHERE sub_section_internal_code = (SELECT internal_code FROM sub_section WHERE internal_code=ssi_subSectionInternalCode AND id!=0) ORDER BY id;
END;
/

CREATE OR REPLACE PROCEDURE getAllSubSectionNames (cursorSubSectionNames OUT SYS_REFCURSOR) IS
BEGIN
   OPEN cursorSubSectionNames FOR SELECT * FROM sub_section WHERE id !=0 ORDER BY id;
END;
/

-- Sections

CREATE OR REPLACE PROCEDURE getAllSectionItems (cursorSectionItem OUT SYS_REFCURSOR) IS
BEGIN
   OPEN cursorSectionItem FOR SELECT * FROM item WHERE sub_section_internal_code IN (SELECT internal_code FROM sub_section WHERE id=0) ORDER BY id;
END;
/

CREATE OR REPLACE PROCEDURE getAllSectionSpecificItems (cursorSectionSpecificItem OUT SYS_REFCURSOR, si_subSectionInternalCode IN item.sub_section_internal_code%TYPE, si_sectionId IN sub_section.section_id%TYPE) IS
BEGIN
   OPEN cursorSectionSpecificItem FOR SELECT * FROM item WHERE sub_section_internal_code = (SELECT internal_code FROM sub_section WHERE id=0 AND section_id=si_sectionId) ORDER BY id;
END;
/

-- Inspections

CREATE OR REPLACE PROCEDURE getAllInspectedItemsBetweenDatesItems (cursorItemBetweenDates OUT SYS_REFCURSOR, ii_dateBefore IN date, ii_dateAfter IN date) IS
BEGIN
   OPEN cursorItemBetweenDates FOR SELECT * FROM item WHERE id IN (SELECT item_id FROM item_inspections WHERE "date" BETWEEN ii_dateBefore AND ii_dateAfter);
END;
/

CREATE OR REPLACE PROCEDURE getAllInspectedCategoryItemsBetweenDatesItems (cursorAllInspectedCategoryItemsBetweenDatesItems OUT SYS_REFCURSOR, ii_categoryId IN item.item_categories_id%TYPE, ii_dateBefore IN date, ii_dateAfter IN date) IS
BEGIN
   OPEN cursorAllInspectedCategoryItemsBetweenDatesItems FOR SELECT * FROM item WHERE id IN (SELECT item_id FROM item_inspections WHERE "date" BETWEEN ii_dateBefore AND ii_dateAfter) AND item_categories_id IN (SELECT id FROM item_categories WHERE id=ii_categoryId);
END;
/

CREATE OR REPLACE PROCEDURE getAllNotInspectedItems(cursorAllNotInspectedItems OUT SYS_REFCURSOR) IS
BEGIN
   OPEN cursorAllNotInspectedItems FOR SELECT * FROM item WHERE id NOT IN (SELECT item_id FROM item_inspections);
END;
/

CREATE OR REPLACE PROCEDURE getItemInspectionNum(cursorCategoryItemBetweenDates OUT SYS_REFCURSOR, iin_id IN item_inspections.id%TYPE) IS
BEGIN
   OPEN cursorCategoryItemBetweenDates FOR SELECT COUNT(*) AS total FROM item_inspections WHERE item_id=iin_id;
END;
/